package com.ado.mysql.test.testDB.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ado.mysql.test.testDB.model.ShoppingList;
import com.ado.mysql.test.testDB.repository.ShopItemRepository;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping(value="/users")
public class ShoppingListResource {
	
	@Autowired
	ShopItemRepository shopItemRepository;
	
	//get all list method
	@GetMapping(value="/shoppinglist")
	public List<ShoppingList> getAll() {
		return shopItemRepository.findAll();
	}
	
	//get one with id
	@GetMapping("/shoppinglist/{id}")
    public ShoppingList show(@PathVariable String id){
        int shoppingListId = Integer.parseInt(id);
        ShoppingList shopWithId = shopItemRepository.findById(shoppingListId).orElse(null);
        return shopWithId;
    }
	
	//delete stock with id
	@PutMapping("/shoppinglist/{id}/{num}")
    public ShoppingList update(@PathVariable String id, @PathVariable Integer num){
		int updateShopId = Integer.parseInt(id);
        ShoppingList updateShop = shopItemRepository.findById(updateShopId).orElse(null);
        int newStock = updateShop.getStock()- num;
        updateShop.setStock(newStock);
        return shopItemRepository.save(updateShop);
    }
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	//Receive user email and send it 
		@PutMapping("/shoppinglist/user/email/{email}")
	    public void send(@PathVariable String email){
			System.out.println("Sending Email...");

	        sendEmail(email.toString());

	        System.out.println("Done");
	    }

	void sendEmail(String emailAddr) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailAddr);

        msg.setSubject("Thank you for your Order!");
        msg.setText("Thank you for your ordering at ACME Inventory!\n\nYour items will be shipped soon.");

        javaMailSender.send(msg);

    }
}
