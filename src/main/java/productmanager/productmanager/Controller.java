package productmanager.productmanager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productmanager.productmanager.dto.KPI;
import productmanager.productmanager.dto.OrderResponse;
import productmanager.productmanager.model.*;
import productmanager.productmanager.service.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
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

        List<Customer> customers=service.findCustomerByNameAndEmail(reservation.getName(),reservation.getEmail());
        if(customers.isEmpty()){
            throw new IllegalStateException(" name or email doesn't exists");
        }
        for (Product product:reservation.getProducts()){
            Storage storage=service.findStorageByProductName(product.getName());
            service.updateProductById(storage.getProductName(),storage.getProductQuantityI(),storage.getProductQuantity()- product.getQte(),storage.getProductPrice(),storage.getPromotionPrice(),storage.getProductImage(),storage.getDescription(),storage.getCategory(),storage.getState(),storage.getId());
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
        for (Product product:reservation.getProducts()){
            Storage storage=service.findStorageByProductName(product.getName());
            service.updateProductById(storage.getProductName(),storage.getProductQuantityI(),storage.getProductQuantity()- product.getQte(),storage.getProductPrice(),storage.getPromotionPrice(),storage.getProductImage(),storage.getDescription(),storage.getCategory(),storage.getState(),storage.getId());
        }
        LocalTime now=LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDate dateNow=LocalDate.now();
        reservation.setTime(now);
        reservation.setDate(dateNow);

        float total=0;
            for (Product product:reservation.getProducts()){
                total=total+product.getPrice();
            }
            reservation.setTotal(total);

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
        Admin adObj;
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
        if(customer.getEmail()==null || customer.getName()==null || customer.getGender()==null || customer.getPhoneNumber()==null){
            throw new IllegalStateException("information's missed");
        }
        Customer customer1=service.addCustomer(customer);
        return new ResponseEntity<>(customer1,HttpStatus.CREATED);
    }

    @PostMapping("/registercustomerlogin")
    public ResponseEntity<CustomerLogin> addCustomer(@RequestBody CustomerLogin customerLogin){
        if(customerLogin.getEmail()==null){
            throw new IllegalStateException("you didn't give your email");
        }
            CustomerLogin cEmail=service.findCustomerLoginByEmail(customerLogin.getEmail());
            if (cEmail!=null){
                throw new IllegalStateException("customer with this email: "+cEmail+" already exists");
            }

            CustomerLogin customerLogin1=service.addCustomerLogin(customerLogin);
            return new ResponseEntity<>(customerLogin1,HttpStatus.CREATED);

    }

    @PostMapping("/customerlogin")
    public  ResponseEntity<CustomerLogin> loginCustomer(@RequestBody CustomerLogin customerLogin){
        String custName=customerLogin.getName();
        String custPassword=customerLogin.getPassword();

        if(custName==null && custPassword==null){
            throw new IllegalStateException("please give name and password");
        }
           CustomerLogin custObj=service.findCustomerLoginByNameAndPassword(custName,custPassword);
            if(custObj==null){
                throw new IllegalStateException("login failed");
            }
            return  new ResponseEntity<>(custObj,HttpStatus.ACCEPTED);
    }

    @PostMapping("/command")
    public ResponseEntity<Command> command(@RequestBody Command command){


        List<Customer> customer=service.findCustomerByName(command.getCname());

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
        if (storage.getState().contentEquals("promotion")){
            command.setPrice(storage.getPromotionPrice()*command.getQte());
        }else{
            command.setPrice(storage.getProductPrice()*command.getQte());}

        Command obj=service.command(command);
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

        service.updateProductById(storage.getProductName(),storage.getProductQuantityI(),storage.getProductQuantity(),storage.getProductPrice(),storage.getPromotionPrice(),storage.getProductImage(),
        storage.getDescription(),storage.getCategory(),storage.getState(),storage.getId());
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

    @GetMapping("/pdfReclamation")
    public void reclamationPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=List-of-reclamations.pdf";
        response.setHeader(headerKey, headerValue);
        this.service.exportReclamation(response);
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

    @DeleteMapping("/deleteCom/{id}")
    public void deleteCom(@PathVariable("id") int id){
        service.deleteComById(id);
    }

    @GetMapping("/kpi/{date}")
    public ResponseEntity<KPI> kpi(@PathVariable("date") String date){
        LocalTime now=LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        KPI kpi=new KPI();
        List<Reservation> reservations=service.findReservationByDateAndTime(LocalDate.parse(date),now);
        List<Reclamation> reclamations=service.findReclamationByDateAndTime(LocalDate.parse(date),now);
        List<Integer> maxId=new ArrayList<>();
        int max;
        if (!reservations.isEmpty()){
            for (Reservation R:reservations){
                maxId.add(R.getId());
            }
            max= Collections.max(maxId);

            float efficiency=(float) 100*(max-reservations.get(0).getId()+1)/500;
            kpi.setEfficiency(efficiency);
        }else{
            kpi.setEfficiency(0);
        }
        List<Integer> maxId1=new ArrayList<>();
        int max1;
        if (!reclamations.isEmpty()) {
            for (Reclamation R:reclamations){
                maxId1.add(R.getId());
            }
            max1=Collections.max(maxId1);
            float PPM=(float) 1000000*(max1-reclamations.get(0).getId()+1)/500;
            kpi.setPPM(PPM);
        }else {
            kpi.setPPM(0);
        }
        return new ResponseEntity<>(kpi,HttpStatus.ACCEPTED);
    }

    @GetMapping("reservations/{date}")
    public ResponseEntity<List<Reservation>> chart(@PathVariable("date") String date){
        LocalTime now=LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        List<Reservation> reservations=service.findReservationByDateAndTime(LocalDate.parse(date),now);
        return new ResponseEntity<>(reservations,HttpStatus.OK);
    }

    @GetMapping("reclamation/{date}")
    public ResponseEntity<List<Reclamation>> chart1(@PathVariable("date") String date){
        LocalTime now=LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        List<Reclamation> reclamations=service.findReclamationByDateAndTime(LocalDate.parse(date),now);
        return new ResponseEntity<>(reclamations,HttpStatus.OK);
    }

    @PostMapping("/addReclamation")
    public ResponseEntity<Reclamation> reclamation(@RequestBody Reclamation reclamation){
        Storage storage=service.findStorageByProductName(reclamation.getProductName());
        CustomerLogin customer=service.findCustomerLoginByNameAndEmail(reclamation.getClientName(),reclamation.getClientEmail());
        List<Customer> customers=service.findCustomerByNameAndEmail(reclamation.getClientName(),reclamation.getClientEmail());
        Reservation reservation=service.findReservationByNameAndEmailAndId(reclamation.getClientName(),reclamation.getClientEmail(),reclamation.getCodeCommand());
        List<Product> products=service.findProductByCp_fkAndName(reclamation.getCodeCommand(),reclamation.getProductName());
        if (customer==null && customers.isEmpty()){
            throw new IllegalStateException("please enter valid name and email");
        }
        if (reservation==null){
            throw new IllegalStateException("Unknown command");
        }
        if (storage==null){
            throw new IllegalStateException("this product doesn't exists");
        }
        if (reclamation.getCodeArticle()!=storage.getId()){
            throw new IllegalStateException("codeArticle incorrect");
        }
        if (products.isEmpty()){
            throw new IllegalStateException("this product doesn't exists in your command");
        }
        reclamation.setDate(LocalDate.now());
        reclamation.setTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
        Reclamation reclamation1=service.addReclamation(reclamation);
        return new ResponseEntity<>(reclamation1,HttpStatus.CREATED);
    }

    @GetMapping("/reclamations")
    public ResponseEntity<List<Reclamation>> listReclamation(){
        List<Reclamation> reclamations=service.findAllReclamation();
        return new ResponseEntity<>(reclamations,HttpStatus.OK);
    }

}
