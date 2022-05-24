package productmanager.productmanager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productmanager.productmanager.dto.KPI;
import productmanager.productmanager.model.*;
import productmanager.productmanager.service.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/productmanager")
public class Controller {
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @PostMapping("/customerloginplaceorder")
    public ResponseEntity<Reservation> loginPlaceOrder(@RequestBody Reservation reservation){
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter dateTimeFormatter2=DateTimeFormatter.ofPattern("yyyy");
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
        reservation.setMonth(dateNow.format(dateTimeFormatter));
        reservation.setYear(dateNow.format(dateTimeFormatter2));

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
    public List<Reservation> responseInfo(){
        return service.getReservations();
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

    @PostMapping("/commandLogin")
    public ResponseEntity<Command> commandL(@RequestBody Command command){



        CustomerLogin customerLogin=service.findCustomerLoginByName(command.getCname());
        Command command1=service.findCommandByName(command.getName());
        if (command1!=null){
            throw new IllegalStateException("you can not command "+command1.getName()+"twice");
        }
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

    @GetMapping("/listCustomerLogins")
    public  ResponseEntity<List<CustomerLogin>> getCustomersLogin(){
        List<CustomerLogin> customerLogins=service.findCustomerLogins();
        return new ResponseEntity<>(customerLogins,HttpStatus.OK);
    }

    @DeleteMapping("/deleteCom/{id}")
    public void deleteCom(@PathVariable("id") int id){
        service.deleteComById(id);
    }

    @GetMapping("/kpi/{month}")
    public ResponseEntity<KPI> kpi(@PathVariable("month") String month){
        KPI kpi=new KPI();
        List<Reservation> reservations=service.findReservationByMonth(month);
        List<Reclamation> reclamations=service.findReclamationByMonth(month);
        List<Storage> storages=service.getStock();
        int total1=0;
        for (Storage s:storages){
            total1+=s.getProductQuantity();
        }
        int total=0;
        if (!reservations.isEmpty()){
            for (Reservation R:reservations){
                for (Product p:R.getProducts()){
                    total += p.getQte();
                }
            }
            float efficiency=(float) 100*(total)/total1;
            kpi.setEfficiency(efficiency);
        }else{
            kpi.setEfficiency(0);
        }
        int total2=0;
        if (!reclamations.isEmpty()) {
            for (Reclamation r:reclamations){
                for (ProductClaimed pc:r.getProductClaimeds()){
                    total2+=pc.getQte();
                }
            }
            if (!reservations.isEmpty()){
                total=0;
            for (Reservation R:reservations){
                for (Product p:R.getProducts()){
                    total += p.getQte();
                }
            }
                float PPM=(float) 100*(total2)/total;
                kpi.setPPM(PPM);
            }else{
                kpi.setPPM((float) 100 * (total2));
            }
        }else {
            kpi.setPPM(0);
        }
        return new ResponseEntity<>(kpi,HttpStatus.ACCEPTED);
    }

    @GetMapping("reservations/{year}")
    public ResponseEntity<List<Reservation>> chart(@PathVariable("year") String year){

        List<Reservation> reservations=service.findReservationByYear(year);
        return new ResponseEntity<>(reservations,HttpStatus.OK);
    }

    @GetMapping("reclamation/{year}")
    public ResponseEntity<List<Reclamation>> chart1(@PathVariable("year") String year){

        List<Reclamation> reclamations=service.findReclamationByYear(year);
        return new ResponseEntity<>(reclamations,HttpStatus.OK);
    }

    @PostMapping("/addReclamation")
    public ResponseEntity<Reclamation> reclamation(@RequestBody Reclamation reclamation){
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter dateTimeFormatter2=DateTimeFormatter.ofPattern("yyyy");
        CustomerLogin customer=service.findCustomerLoginByNameAndEmail(reclamation.getClientName(),reclamation.getClientEmail());

        Reservation reservation=service.findReservationByNameAndEmailAndId(reclamation.getClientName(),reclamation.getClientEmail(),reclamation.getCodeCommand());
        if (customer==null ){
            throw new IllegalStateException("please enter valid name and email");
        }
        if (reservation==null){
            throw new IllegalStateException("Unknown command");
        }
        for (ProductClaimed product:reclamation.getProductClaimeds()) {
            Product product1 = service.findProductByCp_fkAndName(reclamation.getCodeCommand(), product.getName());
            if (product1==null){
                throw new IllegalStateException("this product: "+product.getName()+" doesn't exists in your command");
            }
            if (product.getQte()>product1.getQte()){
                throw new IllegalStateException("quantity provided for: "+product.getName()+" is greater than your command's");
            }
        }
        reclamation.setDate(LocalDate.now());
        reclamation.setTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
        reclamation.setMonth(LocalDate.now().format(dateTimeFormatter));
        reclamation.setYear(LocalDate.now().format(dateTimeFormatter2));
        Reclamation reclamation1=service.addReclamation(reclamation);
        return new ResponseEntity<>(reclamation1,HttpStatus.CREATED);
    }

    @PostMapping("/addReclamSupport")
    public ResponseEntity<ReclamSupport> addRec(@RequestBody ReclamSupport reclamSupport){
        Storage storage=service.findStorageByProductName(reclamSupport.getName());
        if (storage==null){
            throw new IllegalStateException("this product doesn't exists");
        }
        if (reclamSupport.getCodeArticle()!=storage.getId()){
            throw new IllegalStateException("codeArticle incorrect");
        }
        ReclamSupport reclamSupport1=service.addReclamSupport(reclamSupport);
        return new ResponseEntity<>(reclamSupport1,HttpStatus.CREATED);
    }

    @GetMapping("/getReclamsSupport/{cName}")
    public ResponseEntity<List<ReclamSupport>> getReclamsSupport(@PathVariable("cName") String cName){
        List<ReclamSupport> reclamSupportList=service.findReclamSupportByCName(cName);
        return new ResponseEntity<>(reclamSupportList,HttpStatus.OK);
    }
    @DeleteMapping("/deleteReclamSupport")
    public void deleteReclamSupport(){
        service.deleteReclamSupport();
    }

    @DeleteMapping("/deleteReclamSupportById/{id}")
    public void deleteReclamSupportById(@PathVariable("id") int id){
        service.deleteReclamSupprotById(id);
    }
    @GetMapping("/reclamations")
    public ResponseEntity<List<Reclamation>> listReclamation(){
        List<Reclamation> reclamations=service.findAllReclamation();
        return new ResponseEntity<>(reclamations,HttpStatus.OK);
    }

    @GetMapping("customerReservation/{email}")
    public ResponseEntity<List<Reservation>> reservationByCustomer(@PathVariable("email") String email){
        List<Reservation> reservations=service.findReservationbyEmail(email);
        if (reservations.isEmpty()){
            throw new IllegalStateException("you have no reservations");
        }
        return new ResponseEntity<>(reservations,HttpStatus.OK);
    }
    @GetMapping("getCustomer/{name}/{password}")
    public ResponseEntity<CustomerLogin> getCustomer(@PathVariable("name") String name,@PathVariable("password") String password){
        CustomerLogin customerLogin=service.findCustomerLoginByNameAndPassword(name,password);
        return new ResponseEntity<>(customerLogin,HttpStatus.OK);
    }

    @GetMapping("getStockId/{name}")
    public ResponseEntity<Integer> getStorageByName(@PathVariable("name") String name){
        Storage str=service.findStorageByProductName(name);
        return new ResponseEntity<>(str.getId(),HttpStatus.OK);
    }

    @GetMapping("customerReclamation/{email}")
    public ResponseEntity<List<Reclamation>> findReclamation(@PathVariable("email") String email){
        List<Reclamation> reclamations=service.findReclamationByClientEmail(email);
        return new ResponseEntity<>(reclamations,HttpStatus.OK);
    }

    @PutMapping("/editReclam")
    public void editReclam(@RequestBody Reclamation reclamation){
        service.editReclamById("clôturé",reclamation.getId());
        LocalDate now=LocalDate.now();
        service.editReclamDateById(now,reclamation.getId());
    }

    @GetMapping("commandByCategory/{year}/{category}")
    public ResponseEntity<List<Reservation>> listReservations(@PathVariable("year") String year,@PathVariable("category") String category){
        List<Reservation> reservations=service.findReservationByYear(year);
        List<Reservation> categories=new ArrayList<>();
        for (Reservation R:reservations){
            CustomerLogin customer=service.findCustomerLoginByEmail(R.getEmail());
            if (Objects.equals(customer.getCategory(), category)){
                categories.add(R);
            }
        }
        return new ResponseEntity<>(categories,HttpStatus.ACCEPTED);
    }
    @GetMapping("commandByCategory2/{month}/{category}")
    public ResponseEntity<List<Reservation>> listReservations2(@PathVariable("month") String month,@PathVariable("category") String category){
        List<Reservation> reservations=service.findReservationByMonth(month);
        List<Reservation> categories=new ArrayList<>();
        for (Reservation R:reservations){
            CustomerLogin customer=service.findCustomerLoginByEmail(R.getEmail());
            if (Objects.equals(customer.getCategory(), category)){
                categories.add(R);
            }
        }
        return new ResponseEntity<>(categories,HttpStatus.ACCEPTED);
    }

    @GetMapping("/productsClaimedByDate/{month}")
    public ResponseEntity<List<ProductClaimed>> reclamations(@PathVariable("month") String month){
        List<ProductClaimed> reclamations=service.findProductClaimedByMonth(month);
        return new ResponseEntity<>(reclamations,HttpStatus.OK);
    }
}
