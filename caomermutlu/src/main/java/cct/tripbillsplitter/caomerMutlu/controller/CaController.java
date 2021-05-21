package cct.tripbillsplitter.caomerMutlu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import cct.tripbillsplitter.caomerMutlu.exceptions.UnauthorizedException;
import cct.tripbillsplitter.caomerMutlu.exceptions.UnprocessableEntityException;
import cct.tripbillsplitter.caomerMutlu.model.Item;

import cct.tripbillsplitter.caomerMutlu.model.User;
import cct.tripbillsplitter.caomerMutlu.util.JWTIssuer;
import io.jsonwebtoken.Claims;

@RestController
@CrossOrigin("*")
public class CaController {

	private Map<String, ArrayList<Item>> trips;
//	private Map<String, ArrayList<Item>> summary;
	private List<User> users = new ArrayList<>();
//	private ArrayList<String> deletedTrip = new ArrayList<String>();
    private Set<String>deletedTrip=new HashSet<String>();
	public CaController() {
		trips = new HashMap<>();
		users.add(new User("Amilcar", "123"));
		users.add(new User("David", "456"));
		users.add(new User("Greg", "789"));
	}

	@GetMapping("/login")
	public String login(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password) {

		/*
		 * if the username and password match with any of userlist then its give a JWT
		 * token to identified the user match this values.
		 */

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().contentEquals(username)) {
				if (users.get(i).getPassword().contentEquals(password)) {

				/*I copied JWT part from David , he showed us how to
				 * handle JWT */	
					return JWTIssuer.createJWT(username, "ca-showcase", username, 86400000);
				}

			}
		}

		/*
		 * if the user name on users arraylist but the password doesnt match the user
		 * password then its throw 401 UnauthorizedException
		 */

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().contentEquals(username)) {
				if (!(users.get(i).getPassword().contentEquals(password))) {
					throw new UnauthorizedException("Username and the password doesn't match");
				}
			}

		}

		/*
		 * for other situation which means if the username its not in the user array
		 * list then its throw exception saying username doesnt exist
		 */
		throw new UnauthorizedException("Username doesn't exist");

	}

	@PostMapping("/{trip}/expense") // Authorization: Bearer <token>
	public Map<String, ArrayList<Item>> addExpense(@PathVariable("trip") String trip,
			@RequestHeader(name = "Authorization", required = true) String token,
			@RequestBody(required = true) Item item) {

		Claims claims = JWTIssuer.decodeJWT(token.split(" ")[1]);
		String subClaim = claims.get("sub", String.class);
		item.setName(subClaim);
		item.setTripID(trip);

		/* Instead of 500 error I changed to 401  */
		/* This check if the user who add expense is authorized user or not
		 * I couldn't use this loop because  it was always throwing error so 
		 * i decide to delete it but when I handling this front-end I post the expense with 
		 * JWT token so I don't really needed  */
//		for (int i = 0; i < users.size(); i++) {
//			if (!(users.get(i).getUsername().contentEquals(subClaim))) {
//				throw new UnauthorizedException("Unknown user");
//			}
//		}

		/*
		 * before then add expense this loop check the trip name it active or not if the
		 * trip added to closed trip then throwing 422 exception
		 */
		for (int i = 0; i < deletedTrip.size(); i++) {
			if (!(deletedTrip.isEmpty())) {
				if (deletedTrip.contains(trip)) {
					throw new UnprocessableEntityException("You can't add expense to deleted trip.");
				}
			}
		}

		/* this check the trip this is a new label or an existing one */
		if (trips.get(trip) == null) {
			trips.put(trip, new ArrayList<Item>());
		}

		/*
		 * If the trip name already exist then it's add the expense to given trip name
		 */
		trips.get(trip).add(item);

		return trips;
	}

	@GetMapping("/{trip}")
	public ArrayList<Item> getTrip(@PathVariable("trip") String trip) {

		/*
		 * if the given trip Id is empty which mean there is no expense given tripId
		 * then throw 204 No Content exception. But I couldn't display error message
		 * when I throw there was no message and when I am dealing the front-end I
		 * couldn't display message and I had Uncaught (in promise) SyntaxError:
		 * Unexpected end of JSON input error so I changed 204 No Content to 422
		 * UnprocessableEntity to able to display error message
		 *
		 **/
//		if (trips.get(trip) == null) {
//			throw new NoContent("No Content");
//		}
		if (trips.get(trip) == null) {
			throw new UnprocessableEntityException("there is no trip given trip name");
		}
		/* it's returning the given trip ArrayList */
		return trips.get(trip);
	}

	@PostMapping("/{trip}/close")
	public Set<String> closeTrip(@PathVariable("trip") String trip) {

		/*
		 * if there is an trip name as given trip name then add this trip to closed trip
		 * arrayList and after not allowed to add expense to that trip no more. If there
		 * is no trip given name then throw 422 UnprocessableException
		 */
		if (trips.get(trip) == null) {
			throw new UnprocessableEntityException("There is no trip to close given trip name");
		}
		/*I used before ArrayList then to able to avoid dublicate value i changed this
		 * HashSet */
		deletedTrip.add(trip);
		return deletedTrip;
	}

	@GetMapping("/{trip}/summary")
	public Map<String, ArrayList<Item>> getSummary(@PathVariable("trip") String trip) {

		/* It was 204 No content before same reason as I changed to 
		 * get trip exception 204 to 422 ,I changed this exception 204 to 422 
		 * to able to handling error message  */
		
//		if (trips.get(trip) == null) {
//			throw new NoContentException("No Content");
//		}

		if (trips.get(trip) == null) {
			throw new UnprocessableEntityException("There is no trip to show given trip name");
		}
		
          
//		ArrayList<Summary> summary = new ArrayList<>();
		Map<String, ArrayList<Item>> summary=new HashMap<>();
        summary.put(trip,new ArrayList<Item>());  
		for(int i=0;i<trips.get(trip).size();i++) {
		summary.get(trip).add(trips.get(trip).get(i));
		}

		
		

		
/* Created local variable name sum1 to add all the expense user1 did in this case user1 Amilcar,
 *  sum2 to add all the expense user2 did in this case user2 David,
 *  sum3 to add all the expense user1 did in this case user3 Greg 
 *  and looped  given trip and add all the expense users did.
 *  Also checked each expense  and signed the low and high payment */		
		
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		int low = 10000;
		int high = 0;
		int purhace = summary.get(trip).size();
		for (int i = 0; i <  summary.get(trip).size(); i++) {
			if (summary.get(trip).get(i).getName().contentEquals("Amilcar")) {

				sum1 += summary.get(trip).get(i).getPrice();

				if (summary.get(trip).get(i).getPrice() < low) {
					low = summary.get(trip).get(i).getPrice();
				}

				if (summary.get(trip).get(i).getPrice() > high) {
				      high=	summary.get(trip).get(i).getPrice();
				}

			}

			if (summary.get(trip).get(i).getName().contentEquals("David")) {
				sum2 += summary.get(trip).get(i).getPrice();

				if (summary.get(trip).get(i).getPrice() < low) {
					low = summary.get(trip).get(i).getPrice();
				}

				if (summary.get(trip).get(i).getPrice() > high) {
					high = summary.get(trip).get(i).getPrice();
				}

			}
			if (summary.get(trip).get(i).getName().contentEquals("Greg")) {
				sum3 += summary.get(trip).get(i).getPrice();

				if (summary.get(trip).get(i).getPrice() < low) {
					low = summary.get(trip).get(i).getPrice();
				}
				if (summary.get(trip).get(i).getPrice() > high) {
					high = summary.get(trip).get(i).getPrice();
				}
			}
		}

		/* I create local variable sumAll which is total expense all the user did given trip
		 * int user1 shows the subraction of user1 paid and average payment 
		 * depending of this number we can decide if the user should paid or get paid  */
		
		int sumAll = sum1 + sum2 + sum3;
		int user1 = sum1 - (sumAll / 3);
		int user2 = sum2 - (sumAll / 3);
		int user3 = sum3 - (sumAll / 3);
		
		
		String str1 = "";
		String str2 = "";
		String str3 = "";

		if (user1 < 0) {
			str1 = "should paid;";
		}
		if (user1 == 0) {
			str1 = "even";
		}
		if (user1 > 0) {
			str1 = "should get paid";
		}

		if (user2 < 0) {
			str2 = "should  paid;";
		}
		if (user2 == 0) {
			str2 = "even";
		}
		if (user2 > 0) {
			str2 = "should get paid";
		}
		if (user3 < 0) {
			str3 = "should  paid;";
		}
		if (user3 == 0) {
			str3 = "even";
		}
		if (user3 > 0) {
			str3 = "should  get paid";
		}
		
		
	
		/*I create new key name paymentHistory and add the payment of all user 
		 * lowest paymnet,highest payment etc.. I use different key because when i use 
		 * this data front-end this was easiest way for me  */
		summary.put("paymentHistory", new ArrayList<Item>());
	    summary.get("paymentHistory").add(new Item(trip,"Total  expense Amilcar did on trip",sum1,"Amilcar"));	
	    summary.get("paymentHistory").add(new Item(trip,"Total  expense David did on trip",sum2,"David"));	
	    summary.get("paymentHistory").add(new Item(trip,"Total  expense Greg did on trip",sum3,"Greg"));	
	    summary.get("paymentHistory").add(new Item(trip,"The lowest expense on trip ",low,"LowerstPayment"));	
	    summary.get("paymentHistory").add(new Item(trip,"The Highest expense on trip",high,"HighestPayment"));	
	    summary.get("paymentHistory").add(new Item(trip,"The average expenditure per person ",(sumAll/3),"averageExpense"));	
	    summary.get("paymentHistory").add(new Item(trip,"Total purhace",purhace,"purhace"));	
	    
	    
	    summary.put("userPayment", new ArrayList<Item>());
	    
	 /* I create new string here for each user  how musch they paid and how much they should pay or
	  * should get paid */
	    String str01="Total expense Amilcar did for this trip is : "+sum1+", the average expenditure per person is : "+(sumAll/3)+".Amilcar "+str1+" "+Math.abs(user1);
	    String str02="Total expense David did for this trip is : "+sum2+", the average expenditure per person is : "+(sumAll/3)+".David "+str2+" "+Math.abs(user2);
	    String str03="Total expense Greg did for this trip is : "+sum3+", the average expenditure per person is : "+(sumAll/3)+".Greg "+str3+" "+Math.abs(user3);

	    summary.get("userPayment").add(new Item(trip,str01,user1,"Amilcar"));
	    summary.get("userPayment").add(new Item(trip,str02,user2,"David"));
	    summary.get("userPayment").add(new Item(trip,str03,user3,"Greg"));


		return summary;
	}
	
	
}
