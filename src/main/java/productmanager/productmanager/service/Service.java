package productmanager.productmanager.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;

import productmanager.productmanager.model.*;
import productmanager.productmanager.repo.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;


@org.springframework.stereotype.Service
@Transactional
public class Service {

    private final CustomerLoginRepo customerLoginRepo;
    private final ProductRepo productRepo;
    private final AdminRepo adminRepo;
    private final ReservationRepo reservationRepo;
    private final CommandRepo commandRepo;
    private final StorageRepo storageRepo;
    private final ReclamationRepo reclamationRepo;
    private final ReclamSupportRepo reclamSupportRepo;
    private  final ProductClaimedRepo productClaimedRepo;

    @Autowired
    public Service(CustomerLoginRepo customerLoginRepo, ProductRepo productRepo, AdminRepo adminRepo, ReservationRepo reservationRepo, CommandRepo commandRepo, StorageRepo storageRepo, ReclamationRepo reclamationRepo, ReclamSupportRepo reclamSupportRepo, ProductClaimedRepo productClaimedRepo) {
        this.customerLoginRepo = customerLoginRepo;
        this.productRepo = productRepo;
        this.adminRepo = adminRepo;
        this.reservationRepo = reservationRepo;
        this.commandRepo = commandRepo;
        this.storageRepo = storageRepo;
        this.reclamationRepo = reclamationRepo;

        this.reclamSupportRepo = reclamSupportRepo;
        this.productClaimedRepo = productClaimedRepo;
    }




    public List<Reservation> findOrders() {

        return reservationRepo.findAll();
    }

    public List<Reservation> getReservations() {
        return  reservationRepo.findAll();
    }

    public Admin findAdminByNameAndPassword(String adminName, String adminPassword) {
        return adminRepo.findAdminByNameAndPassword(adminName,adminPassword);
    }

    public CustomerLogin findCustomerLoginByNameAndPassword(String custName, String custPassword) {
        return customerLoginRepo.findCustomerByNameAndPassword(custName,custPassword);
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

    public List<Storage> getStockByCategory(String category) {
        return storageRepo.findStorageByCategory(category);
    }

    public List<Storage> getStockByState(String state) {
        return storageRepo.findStorageByState(state);
    }


    public CustomerLogin findCustomerLoginByName(String name) {
        return customerLoginRepo.findCustomerLoginByName(name);
    }


    public List<CustomerLogin> findCustomerLogins() {
        return customerLoginRepo.findAll();
    }

    public void deleteComById(int id) {
        commandRepo.deleteCommandById(id);
    }

    public List<Reservation> findReservationByMonth(String month) {
        return reservationRepo.findReservationByMonth(month);
    }


    public Reclamation addReclamation(Reclamation reclamation) {
        return reclamationRepo.save(reclamation);
    }

    public List<Reclamation> findAllReclamation() {
        return reclamationRepo.findAll();
    }

    public List<Reclamation> findReclamationByYear(String year) {
        return reclamationRepo.findReclamationByYear(year);
    }

    public Reservation findReservationByNameAndEmailAndId(String clientName, String clientEmail, int codeCommand) {
        return reservationRepo.findReservationByNameAndEmailAndId(clientName,clientEmail,codeCommand);
    }

    public Product findProductByCp_fkAndName(int codeCommand, String productName ) {
        return productRepo.findProductByCp_fkAndName(codeCommand,productName);
    }


    public List<Reservation> findReservationbyEmail(String email) {
        return reservationRepo.findReservationByEmail(email);
    }

    public List<Reclamation> findReclamationByClientEmail(String email) {
        return reclamationRepo.findReclamationByClientEmail(email);
    }

    public ReclamSupport addReclamSupport(ReclamSupport reclamSupport) {
        return reclamSupportRepo.save(reclamSupport);
    }

    public void deleteReclamSupport() {
        reclamSupportRepo.deleteAll();
    }

    public void deleteReclamSupprotById(int id) {
        reclamSupportRepo.deleteReclamSupportById(id);
    }

    public List<ReclamSupport> findReclamSupportByCName(String cName) {
        return reclamSupportRepo.findReclamSupportBycName(cName);
    }

    public Command findCommandByName(String name) {
        return commandRepo.findCommandByName(name);
    }

    public void editReclamById(String etat, int id) {
        reclamationRepo.editReclamById(etat,id);
    }

    public List<ProductClaimed> findProductClaimedByMonth(String month) {
        return productClaimedRepo.findProductClaimedByMonth(month);
    }

    public void editReclamDateById(LocalDate now, int id) {
        reclamationRepo.editReclamDateById(now,id);
    }

    public List<Reservation> findReservationByYear(String year) {
        return reservationRepo.findReservationByYear(year);
    }

    public List<Reclamation> findReclamationByMonth(String month) {
        return reclamationRepo.findReclamationByMonth(month);
    }

    public List<Reservation> findReservationByYearAndCategory(String year,String category) {
        return reservationRepo.findReservationByYearAndCategory(year,category);
    }
}
