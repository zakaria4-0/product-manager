package productmanager.productmanager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productmanager.productmanager.dto.OrderRequest;
import productmanager.productmanager.dto.OrderResponse;
import productmanager.productmanager.model.*;
import productmanager.productmanager.service.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/productmanager")
public class Controller {
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @PostMapping("/customerplaceorder")
    public ResponseEntity<Reservation> placeOrder(@RequestBody Reservation reservation){

        Customer objEmail=service.findCustomerByNameAndEmail(reservation.getName(),reservation.getEmail());
        if(objEmail==null){
            throw new IllegalStateException(" name or email doesn't exists");
        }

        service.customerPlaceOrder(reservation);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PostMapping("/customerloginplaceorder")
    public ResponseEntity<Reservation> loginPlaceOrder(@RequestBody Reservation reservation){
        CustomerLogin objEmail=service.findCustomerLoginByNameAndEmail(reservation.getName(),reservation.getEmail());
        if(objEmail==null){
            throw new IllegalStateException("name or email doesn't exists");
        }
        service.customerPlaceOrder(reservation);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/findallorders")
    public List<Reservation> findAllOrders(){
        return service.findOrders();
    }

    @GetMapping("/getinfo")
    public List<OrderResponse> responseInfo(){
        return service.getInfo();
    }

    @PostMapping("/adminloging")
    public ResponseEntity<Admin> loginAdmin(@RequestBody Admin admin){
        String adminName=admin.getName();
        String adminPassword=admin.getPassword();
        Admin adObj=null;
        if(adminName!=null && adminPassword!=null){
            adObj=service.findAdminByNameAndPassword(adminName,adminPassword);
            if(adObj==null){
                throw new IllegalStateException("login failed");
            }
            return new ResponseEntity<>(adObj, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping("/registercustomer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        if(customer.getEmail()!=null && customer.getName()!=null && customer.getGender()!=null && customer.getPhoneNumber()!=null){

                Customer cEmail=service.addCustomer(customer);
            return new ResponseEntity<>(cEmail,HttpStatus.CREATED);
        }
        return new ResponseEntity("please give all your informations",HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping("/registercustomerlogin")
    public ResponseEntity<CustomerLogin> addCustomer(@RequestBody CustomerLogin customerLogin){
        if(customerLogin.getEmail()!=null){
            CustomerLogin cEmail=service.findCustomerLoginByEmail(customerLogin.getEmail());
            if (cEmail!=null){
                throw new IllegalStateException("customer with this email: "+cEmail+" already exists");
            }
            cEmail=null;
            cEmail=service.addCustomerLogin(customerLogin);
            return new ResponseEntity<>(cEmail,HttpStatus.CREATED);
        }
        return new ResponseEntity("you didn't giv your email",HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping("/customerlogin")
    public  ResponseEntity<CustomerLogin> loginCustomer(@RequestBody CustomerLogin customerLogin){
        String custName=customerLogin.getName();
        String custPassword=customerLogin.getPassword();
        CustomerLogin custObj=null;
        if(custName!=null && custPassword!=null){
            custObj=service.findCustomerLoginByNameAndPassword(custName,custPassword);
            if(custObj==null){
                throw new IllegalStateException("login failed");
            }
            return  new ResponseEntity<>(custObj,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping("/command")
    public ResponseEntity<Command> command(@RequestBody Command command){


        Customer customer=service.findCustomerByName(command.getCname());

        if(customer==null){
            throw new IllegalStateException("Invalid name ");
        }
        Storage storage=service.findStorageByProductName(command.getName());
            if(storage==null){
                throw new IllegalStateException(" this product doesn't exists in stock");
            }else {
                if(storage.getProductQuantity()<command.getQte()){
                    throw new IllegalStateException(" sold out");
                }
            }
        if (storage.getState()=="promotion"){
            command.setPrice(storage.getPromotionPrice()*command.getQte());
        }else{
            command.setPrice(storage.getProductPrice()*command.getQte());}

        Command obj=service.command(command);
        service.updateProductByProductName(storage.getProductQuantityI(),storage.getProductQuantity()-command.getQte(),storage.getProductPrice(),storage.getPromotionPrice(),storage.getProductImage(),storage.getDescription(),storage.getCategory(),storage.getState(),storage.getProductName());
        return new ResponseEntity<>(obj,HttpStatus.CREATED);
    }
    @PostMapping("/commandLogin")
    public ResponseEntity<Command> commandL(@RequestBody Command command){



        CustomerLogin customerLogin=service.findCustomerLoginByName(command.getCname());
        if(customerLogin==null){
            throw new IllegalStateException("Invalid name ");
        }
        Storage storage=service.findStorageByProductName(command.getName());
        if(storage==null){
            throw new IllegalStateException(" this product doesn't exists in stock");
        }
        if(storage.getProductQuantity()<command.getQte()){
                throw new IllegalStateException(" sold out");
        }

        if (storage.getState().contentEquals("promotion")) {
            command.setPrice(storage.getPromotionPrice()*command.getQte());
        }else{
        command.setPrice(storage.getProductPrice()*command.getQte());}

        Command obj=service.command(command);
        service.updateProductByProductName(storage.getProductQuantityI(),storage.getProductQuantity()-command.getQte(),storage.getProductPrice(),storage.getPromotionPrice(),storage.getProductImage(),storage.getDescription(),storage.getCategory(),storage.getState(),storage.getProductName());
        return new ResponseEntity<>(obj,HttpStatus.CREATED);
    }
    @GetMapping("/getCommand/{cname}")
    public ResponseEntity<List<Command>> getCommand(@PathVariable("cname") String cname){
        List<Command> obj=service.getCommand(cname);
        return new ResponseEntity<>(obj,HttpStatus.OK);
    }

    @DeleteMapping("/deleteCommand")
    public void deleteCommand(){
        service.deleteCommand();
    }

    @PostMapping("/addToStock")
    public ResponseEntity<Storage> addToStock(@RequestBody Storage storage){
        Storage stockVer=service.findStorageByProductName(storage.getProductName());
        if(stockVer!=null){
            throw new IllegalStateException("Product already exists");
        }
        Storage stock=service.addProductToStock(storage);
        return new ResponseEntity<>(stock,HttpStatus.CREATED);
    }
    @GetMapping("/getStock")
    public ResponseEntity<List<Storage>> getStock(){
        List<Storage> stock=service.getStock();
        return new ResponseEntity<>(stock,HttpStatus.OK);
    }

    @GetMapping("/getstock-category/{category}")
    public ResponseEntity<List<Storage>> getStockByCategory(@PathVariable("category") String category){
        List<Storage> stock=service.getStockByCategory(category);
        return new ResponseEntity<>(stock,HttpStatus.OK);
    }

    @GetMapping("/getstock-state/{state}")
    public ResponseEntity<List<Storage>> getStockByState(@PathVariable("state") String state){
        List<Storage> stock=service.getStockByState(state);
        return new ResponseEntity<>(stock,HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable("id") int id){
        service.deleteProduct(id);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Storage> updateProduct(@RequestBody Storage storage){

        service.updateProductByProductName(storage.getProductQuantityI(),storage.getProductQuantity(),storage.getProductPrice(),storage.getPromotionPrice(),storage.getProductImage(),
        storage.getDescription(),storage.getCategory(),storage.getState(),storage.getProductName());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/pdfGenerate")
    public void generatePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=List-of-commands.pdf";
        response.setHeader(headerKey, headerValue);
        this.service.export(response);
    }
    @GetMapping("/pdfStock")
    public void stockPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=List-of-products.pdf";
        response.setHeader(headerKey, headerValue);
        this.service.exportStock(response);
    }
    @GetMapping("/pdfCustomers")
    public void customersPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=List-of-customers.pdf";
        response.setHeader(headerKey, headerValue);
        this.service.exportCustomers(response);
    }

    @GetMapping("/listCustomers")
    public ResponseEntity<List<Customer>> getCustomers(){
        List<Customer> customers=service.findcustomers();
        return new ResponseEntity<>(customers,HttpStatus.OK);
    }

    @GetMapping("/listCustomerLogins")
    public  ResponseEntity<List<CustomerLogin>> getCustomersLogin(){
        List<CustomerLogin> customerLogins=service.findCustomerLogins();
        return new ResponseEntity<>(customerLogins,HttpStatus.OK);
    }




}
