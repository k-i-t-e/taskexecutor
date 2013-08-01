package taskexecutor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PiCalculator{
	/**
	 *
	 * @author ThomasSatterthwaite
	 */

	static int odd=1;

	/*public static void main(String[] args) {
	    System.out.println("Please wait while I calculate pi...");
	    calculatePi();
	    System.out.println("I have successfully calculated pi.");
	}*/
	public void calculatePi() {
	    BigInteger firstFactorial;
	    BigInteger secondFactorial;
	    BigInteger firstMultiplication;
	    BigInteger firstExponent;
	    BigInteger secondExponent;
	    int firstNumber = 1103;
	    BigInteger firstAddition;
	    BigDecimal currentPi = BigDecimal.ONE;
	    BigDecimal pi = BigDecimal.ZERO;
	    BigDecimal one = BigDecimal.ONE;
	    int secondNumber = 2;
	    double thirdNumber = Math.sqrt(2.0);
	    int fourthNumber = 9801;
	    BigDecimal prefix = BigDecimal.ONE;
	    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	    for(int i=0;i<1000;i++){
	        firstFactorial = factorial(4*i);
	        secondFactorial = factorial(i);
	        firstMultiplication = BigInteger.valueOf(26390*i);
	        firstExponent = exponent(secondFactorial, 4);
	        secondExponent = exponent(BigInteger.valueOf(396),4*i);
	        firstAddition = BigInteger.valueOf(firstNumber).add(firstMultiplication);
	        currentPi = currentPi.add(new BigDecimal(firstFactorial.multiply(firstAddition)).divide(new BigDecimal(firstExponent.multiply(secondExponent)), new MathContext(10000)));
	        Date date=new Date();
	        System.out.println("Interation: " + i + " at " + dateFormat.format(date));
	    }

	    prefix =new BigDecimal(secondNumber*thirdNumber);
	    prefix = prefix.divide(new BigDecimal(fourthNumber), new MathContext(1000));

	    currentPi = currentPi.multiply(prefix, new MathContext(1000));

	    pi = one.divide(currentPi, new MathContext(1000));

	    System.out.println("Pi is: " + pi);

	    return;
	}
	public static BigInteger factorial(int a) {

	    BigInteger result=new BigInteger("1");
	    BigInteger smallResult = new BigInteger("1");
	    long x=a;
	    if (x==1) return smallResult;
	    while(x>1)
	    {
	        result= result.multiply(BigInteger.valueOf(x));

	       x--;
	    }
	    return result;
	}
	public static BigInteger exponent(BigInteger a, int b) {
	    BigInteger answer=new BigInteger("1");

	    for(int i=0;i<b;i++) {
	        answer = answer.multiply(a);
	    }

	    return answer;
	}

}
