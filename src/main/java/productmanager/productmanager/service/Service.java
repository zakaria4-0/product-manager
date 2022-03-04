package productmanager.productmanager.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import productmanager.productmanager.dto.OrderResponse;
import productmanager.productmanager.model.*;
import productmanager.productmanager.repo.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;


@org.springframework.stereotype.Service
@Transactional
public class Service {
    private final CustomerRepo customerRepo;
    private final CustomerLoginRepo customerLoginRepo;
    private final ProductRepo productRepo;
    private final AdminRepo adminRepo;
    private final ReservationRepo reservationRepo;
    private final CommandRepo commandRepo;
    private final StorageRepo storageRepo;

    @Autowired
    public Service(CustomerRepo customerRepo, CustomerLoginRepo customerLoginRepo, ProductRepo productRepo, AdminRepo adminRepo, ReservationRepo reservationRepo, CommandRepo commandRepo, StorageRepo storageRepo) {
        this.customerRepo = customerRepo;
        this.customerLoginRepo = customerLoginRepo;
        this.productRepo = productRepo;
        this.adminRepo = adminRepo;
        this.reservationRepo = reservationRepo;
        this.commandRepo = commandRepo;
        this.storageRepo = storageRepo;
    }




    public List<Reservation> findOrders() {
        return reservationRepo.findAll();
    }

    public List<OrderResponse> getInfo() {
        return  reservationRepo.getInformation();
    }

    public Admin findAdminByNameAndPassword(String adminName, String adminPassword) {
        return adminRepo.findAdminByNameAndPassword(adminName,adminPassword);
    }

    public CustomerLogin findCustomerLoginByNameAndPassword(String custName, String custPassword) {
        return customerLoginRepo.findCustomerByNameAndPassword(custName,custPassword);
    }

    public List<Customer> findCustomerByNameAndEmail(String name, String email) {
        return customerRepo.findCustomerByNameAndEmail(name,email);
    }

    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }



    public CustomerLogin findCustomerLoginByNameAndEmail(String name,String email) {
        return customerLoginRepo.findCustomerLoginByNameAndEmail(name,email);
    }

    public CustomerLogin addCustomerLogin(CustomerLogin customerLogin) {
        return customerLoginRepo.save(customerLogin);
    }

    public Reservation customerPlaceOrder(Reservation reservation) {
        return reservationRepo.save(reservation);
    }

    public Customer findCustomerByEmail(String email) {
        return customerRepo.findCustomerByEmail(email);
    }

    public CustomerLogin findCustomerLoginByEmail(String email) {
        return customerLoginRepo.findCustomerLoginByEmail(email);
    }

    public Command command(Command command) {
        return commandRepo.save(command);
    }

    public List<Command> getCommand(String cname) {
        return commandRepo.findCommandByCname(cname);
    }

    public Storage addProductToStock(Storage storage) {
        return storageRepo.save(storage);
    }

    public List<Storage> getStock() {
        return storageRepo.findAll();
    }

    public void updateProductByProductName(String productName,int productQuantityI,int productQuantity, float productPrice,float promotionPrice,String productImage,String description,String category,String state ,int id) {
        storageRepo.updateProductById(productName,productQuantityI,productQuantity,productPrice,promotionPrice,productImage,description,category,state,id);
    }

    public Storage findStorageByProductName(String name) {
        return storageRepo.findStorageByProductName(name);
    }

    public void deleteCommand() {
        commandRepo.deleteAll();
    }

    public void deleteProduct(int id) {
        storageRepo.deleteStorageById(id);
    }

    public void export(HttpServletResponse response) throws IOException {
        Document document=new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(30);


        Paragraph paragraph = new Paragraph("List of commands:", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);



        document.add(paragraph);

        document.add(Chunk.NEWLINE);


        PdfPTable table= new PdfPTable(10);
        table.setLockedWidth(true);
        table.setTotalWidth(580f);
        float[] widths=new float[]{5f,11f,30f,18f,14f,12f,10f,20f,10f,19f};
        table.setWidths(widths);
        Font fontTable=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell c1=new PdfPCell(new Phrase("Id",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Name",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Email",fontTable));

        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Date",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Address",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Region",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("City",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("ProductName",fontTable));

        table.addCell(c1);


        c1=new PdfPCell(new Phrase("Quantity",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Price",fontTable));
        table.addCell(c1);
        table.setHeaderRows(1);

        List<Reservation> reservations=reservationRepo.findAll();
        for (int i=0;i<reservations.size();i++){
            for (int j=0;j<reservations.get(i).getProducts().size();j++){
                table.addCell(String.valueOf(reservations.get(i).getId()));
            table.addCell(reservations.get(i).getName());
            table.addCell(reservations.get(i).getEmail());
            table.addCell(reservations.get(i).getDate().toString());
            table.addCell(reservations.get(i).getAddress());
            table.addCell(reservations.get(i).getRegion());
            table.addCell(reservations.get(i).getVille());
            table.addCell(reservations.get(i).getProducts().get(j).getName());
            table.addCell(String.valueOf(reservations.get(i).getProducts().get(j).getQte()));
            table.addCell(String.valueOf(reservations.get(i).getProducts().get(j).getPrice())+" $");}
        }
        document.add(table);
        document.close();
    }
    public void exportStock(HttpServletResponse response) throws IOException {
        Document document=new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(30);


        Paragraph paragraph = new Paragraph("List of Products:", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);



        document.add(paragraph);

        document.add(Chunk.NEWLINE);


        PdfPTable table= new PdfPTable(8);
        table.setLockedWidth(true);
        table.setTotalWidth(570f);

        Font fontTable=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell c1=new PdfPCell(new Phrase("ID",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Product-Name",fontTable));

        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Initial-Quantity",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Current-Quantity",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Price",fontTable));
        table.addCell(c1);
        c1=new PdfPCell(new Phrase("Promotion",fontTable));
        table.addCell(c1);
        c1=new PdfPCell(new Phrase("Category",fontTable));
        table.addCell(c1);
        c1=new PdfPCell(new Phrase("State",fontTable));
        table.addCell(c1);


        table.setHeaderRows(1);

        List<Storage> products=storageRepo.findAll();
        for (int i=0;i<products.size();i++){

                table.addCell(String.valueOf(products.get(i).getId()));
                table.addCell(products.get(i).getProductName());
                table.addCell(String.valueOf(products.get(i).getProductQuantityI()));
                table.addCell(String.valueOf(products.get(i).getProductQuantity()));
                table.addCell(String.valueOf(products.get(i).getProductPrice())+" $");
                table.addCell(String.valueOf(products.get(i).getPromotionPrice())+" $");
                table.addCell(products.get(i).getCategory());
                table.addCell(products.get(i).getState());

        }
        document.add(table);
        document.close();
    }
    public void exportCustomers(HttpServletResponse response) throws IOException {
        Document document=new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(30);


        Paragraph paragraph = new Paragraph("List of Customers:", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);



        document.add(paragraph);

        document.add(Chunk.NEWLINE);


        PdfPTable table= new PdfPTable(3);
        table.setLockedWidth(true);
        table.setTotalWidth(570f);

        Font fontTable=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell c1=new PdfPCell(new Phrase("Name",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Email",fontTable));

        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Phone",fontTable));
        table.addCell(c1);

        table.setHeaderRows(1);

        List<Customer> customers=customerRepo.findAll();
        List<CustomerLogin> customerLogins=customerLoginRepo.findAll();

        for (int i=0;i<customers.size();i++){

            table.addCell(customers.get(i).getName());
            table.addCell(customers.get(i).getEmail());
            table.addCell(customers.get(i).getPhoneNumber());

        }
        for (int i=0;i<customerLogins.size();i++){

            table.addCell(customerLogins.get(i).getName());
            table.addCell(customerLogins.get(i).getEmail());
            table.addCell(customerLogins.get(i).getPhoneNumber());

        }
        document.add(table);
        document.close();
    }

    public List<Storage> getStockByCategory(String category) {
        return storageRepo.findStorageByCategory(category);
    }

    public List<Storage> getStockByState(String state) {
        return storageRepo.findStorageByState(state);
    }

    public List<Customer> findCustomerByName(String name) {
        return customerRepo.findCustomerByName(name);
    }

    public CustomerLogin findCustomerLoginByName(String name) {
        return customerLoginRepo.findCustomerLoginByName(name);
    }

    public List<Customer> findcustomers() {
        return customerRepo.findAll();
    }

    public List<CustomerLogin> findCustomerLogins() {
        return customerLoginRepo.findAll();
    }
}
