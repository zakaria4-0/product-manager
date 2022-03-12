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
import java.time.LocalDate;
import java.time.LocalTime;
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
    private final ReclamationRepo reclamationRepo;


    @Autowired
    public Service(CustomerRepo customerRepo, CustomerLoginRepo customerLoginRepo, ProductRepo productRepo, AdminRepo adminRepo, ReservationRepo reservationRepo, CommandRepo commandRepo, StorageRepo storageRepo, ReclamationRepo reclamationRepo) {
        this.customerRepo = customerRepo;
        this.customerLoginRepo = customerLoginRepo;
        this.productRepo = productRepo;
        this.adminRepo = adminRepo;
        this.reservationRepo = reservationRepo;
        this.commandRepo = commandRepo;
        this.storageRepo = storageRepo;
        this.reclamationRepo = reclamationRepo;
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

    public void customerPlaceOrder(Reservation reservation) {
        reservationRepo.save(reservation);
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

    public void updateProductById(String productName, int productQuantityI, int productQuantity, float productPrice, float promotionPrice, String productImage, String description, String category, String state , int id) {
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
        Font fontTable = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTable.setSize(8);
        Font fontTableRows = FontFactory.getFont(FontFactory.HELVETICA);
        fontTableRows.setSize(8);

        Paragraph paragraph = new Paragraph("List of commands:", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);



        document.add(paragraph);

        document.add(Chunk.NEWLINE);


        PdfPTable table= new PdfPTable(12);
        table.setLockedWidth(true);
        table.setTotalWidth(580f);
        float[] widths=new float[]{5f,11f,26f,15f,12f,14f,12f,10f,20f,10f,15f,19f};
        table.setWidths(widths);
        PdfPCell c1=new PdfPCell(new Phrase("Id",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Name",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Email",fontTable));

        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Date",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Time",fontTable));
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
        c1=new PdfPCell(new Phrase("Total",fontTable));
        table.addCell(c1);
        table.setHeaderRows(1);

        List<Reservation> reservations=reservationRepo.findAll();
        for (Reservation reservation : reservations) {
            for (int j = 0; j < reservation.getProducts().size(); j++) {
                table.addCell(new Phrase(String.valueOf(reservation.getId()), fontTableRows));
                table.addCell(new Phrase(reservation.getName(), fontTableRows));
                table.addCell(new Phrase(reservation.getEmail(), fontTableRows));
                table.addCell(new Phrase(reservation.getDate().toString(), fontTableRows));
                table.addCell(new Phrase(reservation.getTime().toString(), fontTableRows));
                table.addCell(new Phrase(reservation.getAddress(), fontTableRows));
                table.addCell(new Phrase(reservation.getRegion(), fontTableRows));
                table.addCell(new Phrase(reservation.getVille(), fontTableRows));
                table.addCell(new Phrase(reservation.getProducts().get(j).getName(), fontTableRows));
                table.addCell(new Phrase(String.valueOf(reservation.getProducts().get(j).getQte()), fontTableRows));
                table.addCell(new Phrase(reservation.getProducts().get(j).getPrice() + " $", fontTableRows));
                table.addCell(new Phrase(reservation.getTotal() + " $", fontTableRows));
            }
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
        Font fontTable = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTable.setSize(8);
        Font fontTableRows = FontFactory.getFont(FontFactory.HELVETICA);
        fontTableRows.setSize(8);

        Paragraph paragraph = new Paragraph("List of Products:", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);



        document.add(paragraph);

        document.add(Chunk.NEWLINE);


        PdfPTable table= new PdfPTable(8);
        table.setLockedWidth(true);
        table.setTotalWidth(570f);


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
        for (Storage product : products) {

            table.addCell(new Phrase(String.valueOf(product.getId()), fontTableRows));
            table.addCell(new Phrase(product.getProductName(), fontTableRows));
            table.addCell(new Phrase(String.valueOf(product.getProductQuantityI()), fontTableRows));
            table.addCell(new Phrase(String.valueOf(product.getProductQuantity()), fontTableRows));
            table.addCell(new Phrase(product.getProductPrice() + " $", fontTableRows));
            table.addCell(new Phrase(product.getPromotionPrice() + " $", fontTableRows));
            table.addCell(new Phrase(product.getCategory(), fontTableRows));
            table.addCell(new Phrase(product.getState(), fontTableRows));

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
        Font fontTable = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTable.setSize(8);
        Font fontTableRows = FontFactory.getFont(FontFactory.HELVETICA);
        fontTableRows.setSize(8);

        Paragraph paragraph = new Paragraph("List of Customers:", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);



        document.add(paragraph);

        document.add(Chunk.NEWLINE);


        PdfPTable table= new PdfPTable(3);
        table.setLockedWidth(true);
        table.setTotalWidth(570f);


        PdfPCell c1=new PdfPCell(new Phrase("Name",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Email",fontTable));

        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Phone",fontTable));
        table.addCell(c1);

        table.setHeaderRows(1);

        List<Customer> customers=customerRepo.findAll();
        List<CustomerLogin> customerLogins=customerLoginRepo.findAll();

        for (Customer customer : customers) {

            table.addCell(new Phrase(customer.getName(), fontTableRows));
            table.addCell(new Phrase(customer.getEmail(), fontTableRows));
            table.addCell(new Phrase(customer.getPhoneNumber(), fontTableRows));

        }
        for (CustomerLogin customerLogin : customerLogins) {

            table.addCell(new Phrase(customerLogin.getName(), fontTableRows));
            table.addCell(new Phrase(customerLogin.getEmail(), fontTableRows));
            table.addCell(new Phrase(customerLogin.getPhoneNumber(), fontTableRows));

        }
        document.add(table);
        document.close();
    }
    public void exportReclamation(HttpServletResponse response) throws IOException {
        Document document=new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(30);
        Font fontTable = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTable.setSize(8);
        Font fontTableRows = FontFactory.getFont(FontFactory.HELVETICA);
        fontTableRows.setSize(8);

        Paragraph paragraph = new Paragraph("List of reclamations:", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);



        document.add(paragraph);

        document.add(Chunk.NEWLINE);


        PdfPTable table= new PdfPTable(9);
        table.setLockedWidth(true);
        table.setTotalWidth(580f);
        PdfPCell c1=new PdfPCell(new Phrase("Id",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("clientName",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("clientEmail",fontTable));

        table.addCell(c1);

        c1=new PdfPCell(new Phrase("codeCommand",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("ProductName",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("codeArticle",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Motif",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Date",fontTable));
        table.addCell(c1);

        c1=new PdfPCell(new Phrase("Time",fontTable));

        table.addCell(c1);

        table.setHeaderRows(1);

        List<Reclamation> reclamations=reclamationRepo.findAll();
        for (Reclamation reclamation : reclamations) {

            table.addCell(new Phrase(String.valueOf(reclamation.getId()), fontTableRows));
            table.addCell(new Phrase(reclamation.getClientName(), fontTableRows));
            table.addCell(new Phrase(reclamation.getClientEmail(), fontTableRows));
            table.addCell(new Phrase(String.valueOf(reclamation.getCodeCommand()), fontTableRows));
            table.addCell(new Phrase(reclamation.getProductName(), fontTableRows));
            table.addCell(new Phrase(String.valueOf(reclamation.getCodeArticle()), fontTableRows));
            table.addCell(new Phrase(reclamation.getMotif(), fontTableRows));
            table.addCell(new Phrase(String.valueOf(reclamation.getDate()), fontTableRows));
            table.addCell(new Phrase(String.valueOf(reclamation.getTime()), fontTableRows));
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

    public void deleteComById(int id) {
        commandRepo.deleteCommandById(id);
    }

    public List<Reservation> findReservationByDateAndTime(LocalDate date, LocalTime now) {
        return reservationRepo.findReservationByDateAndTime(date,now);
    }


    public Reclamation addReclamation(Reclamation reclamation) {
        return reclamationRepo.save(reclamation);
    }

    public List<Reclamation> findAllReclamation() {
        return reclamationRepo.findAll();
    }

    public List<Reclamation> findReclamationByDateAndTime(LocalDate date, LocalTime now) {
        return reclamationRepo.findReclamationByDateAndTime(date,now);
    }

    public Reservation findReservationByNameAndEmailAndId(String clientName, String clientEmail, int codeCommand) {
        return reservationRepo.findReservationByNameAndEmailAndId(clientName,clientEmail,codeCommand);
    }

    public List<Product> findProductByCp_fkAndName(int codeCommand,String productName ) {
        return productRepo.findProductByCp_fkAndName(codeCommand,productName);
    }


}
