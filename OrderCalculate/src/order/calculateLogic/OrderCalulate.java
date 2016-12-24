package order.calculateLogic;
/*
==============================================================
(C) Author Sidhant Das  
==============================================================
*/

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class OrderCalulate {
	//Prices are fixed 
	private double itemPriceX=100;
	private double itemPriceY=120;
	private static double totalPrice=0;
	private double itemCountX=0;
	private double itemCountY=0;
	private boolean isPromoAppliedA=false;
	private boolean isPromoAppliedB=false;
	private boolean isPromoAppliedC=false;
	
	public static void main (String args[]){
		try{
			OrderCalulate ord= new OrderCalulate();
			//pass the orderId from console
			ord.itemQuantity();
			//create the order
			//ord.createOrder();
			//iterate through the orderItems
			//ord.getOrderItems();
			//create different kinds of promotions
			ord.choosePromotion();
			//apply the promotions to order lines
			ord.applyRemainingPromo();
			//Calculate the orders
			//ord.caculateOrders();
			System.out.println("Total price of the order is "+totalPrice);
			System.out.println("***** Thanks for shopping at HERE **** !!! Please visit again");
			System.out.println("For furthur information please visit WWW.ecomdemo.COM");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void itemQuantity() throws Exception{
		//Entered the number of items you want to buy
		System.out.print("How many oranges you want to buy : ");
		this.itemCountX=insertInput();
		System.out.print("How many Apples you want to buy : ");
		this.itemCountY=insertInput();
	}
	public int insertInput() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String noOfItems = br.readLine();
		int itemCount=0;
		String pattern="\\d[1-9][0-9].";
		Pattern pat=Pattern.compile(pattern);
		Matcher match=pat.matcher(noOfItems);
		while(match.matches()){
			System.out.print("Enter in number : ");
			noOfItems=br.readLine();
		}
		itemCount=Integer.parseInt((noOfItems));
		return itemCount;
	}
	@Test
	public void insertInputTest() throws Exception{
		assertEquals(1, 1);
		
	}
	
	
	public void choosePromotion() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Choose which promotion to apply from these options ");
		if(!isPromoAppliedA){
			System.out.println("OPTION 1 : Buy X quantity of item for the price of Y ");
		}
		if(!isPromoAppliedB){
			System.out.println("OPTION 2 : 50% off on price of X ");
		}
		if(!isPromoAppliedC){
			System.out.println("OPTION 3 : Buy 1 X & 1 Y for Z ");
		}
		System.out.println("OPTION 4 : Yo I am rich!!! I donot want any promotion 	");
		System.out.print("Which type of promotion you want to apply : ");
		int ip = insertInput();
		while(0<ip && ip>5){
			System.out.print("Please choose the above options(1/2/3/4) again : ");
			ip=insertInput();
		}
		optionCall(ip);
		
	}
	
	@Test
	public void choosepromotionTest(){
		assertEquals(1, 1);
		assertEquals(2, 2);
		assertEquals(3, 3);
		assertEquals(4, 4);
		
	}
	private void optionCall(int input) throws Exception{
	    if (input == 1){
	    	createPromotionTypeA();
	    }
	    else if(input == 2){
	    	createPromotionTypeB();
	    }
	    else if(input == 3){
	    	createPromotionTypeC();
	    }
	    else if(input == 4){
	    	caculateOrders();
	    }
	    
	}
	
	//Calculating the promotionA
	public void createPromotionTypeA() throws Exception{
		
		//Buy X quantity of item for the price of Y (3 kgs of oranges for 1.5 kgs)
		isPromoAppliedA=true;
		if(promoEligibilityA(this.itemCountX)){
			this.itemPriceX=this.itemPriceX*(1.5/3);
			caculateOrders();
		}else{
			System.out.println("We are really sorry that you are not eligible for the promotion So please choose again");
			//increaseQuantity();
			choosePromotion();
		}
	}
	public boolean promoEligibilityA(double totalItem) throws Exception{
		int minimumQuantity=3;
		if(totalItem>=minimumQuantity){
			return true;
		}else{
			return false;
		}
	}
	
	@Test
	public void promoEligibilityTest1() throws Exception{
		assertEquals(true, OrderCalulate.this.promoEligibilityA(3.0));
		assertEquals(false, OrderCalulate.this.promoEligibilityA(1.0));
	}
	
	
	public void createPromotionTypeB() throws Exception{
		//50% off on price of X (Pay 75 for 1 kg of oranges instead of Rs 150)
		this.itemPriceX=this.itemPriceX*(0.5);
		isPromoAppliedB=true;
		caculateOrders();
	}
	
	public void createPromotionTypeC() throws Exception{
		//Buy 1 X & 1 Y for Z (Buy 1 kg of apple and 1 kg of orange for 100)
		isPromoAppliedC=true;
		if(promoEligibilityC(this.itemCountX,this.itemCountY)){
			double diff=(this.itemCountX>this.itemCountY)?this.itemCountY:this.itemCountX;
			totalPrice=(diff)*100+(this.itemCountX-diff)*itemPriceX+(this.itemCountY-diff)*itemPriceY;
			caculateOrders();
			
		}
		else{
			System.out.println("We are really sorry that you are not eligible for this promotion So please choose again from remaining promotion");
			choosePromotion();
		}
	}
	
	public boolean promoEligibilityC(double totalItemX,double totalItemY) throws Exception{
		if(totalItemX >=1 && totalItemY >=1){
			return true;
		}
		else
			return false;
	}
	
	public void applyRemainingPromo() throws Exception{
		choosePromotion();
	}
	
	public double caculateOrders() throws Exception{
		totalPrice=(this.itemCountX*itemPriceX) + (this.itemCountY*itemPriceY);
		System.out.println("Promotion applied and the current price of the order is "+totalPrice);
		return totalPrice;
	}
}
