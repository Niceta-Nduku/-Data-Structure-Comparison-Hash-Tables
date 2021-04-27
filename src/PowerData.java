/**
*	PowerData is an object class.
*	The PowerData Object has a Date/Time, power and voltage
*	@author Niceta Nduku 
*/
public class PowerData {
	public String dateTime;
	public String power;
	public String voltage;


	/**
	* PowerData will create a new item that that store DateTime power and voltage strings
	* @param dateTime String containing date and time
	* @param power 
	* @param voltage 
	*/
	public PowerData( String dateTime, String power, String voltage) { 
		this.dateTime = dateTime;
		this.power = power;
		this.voltage= voltage;
	}

	/**
	*	@return dateTime
	*/
	public String getDateTime() {

		return dateTime;
	}

	/**
	*	@Override
	*/
	public String toString() {

		return dateTime+ " " +power+ " " +voltage;
	}
}