package entities.living;

public class CradleOfFilth extends LivingEntity {
	/* class for lowest hierarchy
	 * CrafleOfFilth has the following variables:
	 * 		String name,
	 * 		String gender,
	 * 		int age,
	 * 		boolean isAlive,
	 * 		int[] currentPosition,
	 * 		int cruelty
	 */
	
	int cruelty; // at cruelty = 3, matures to Imp
	
	public CradleOfFilth(String gender, int[] currentPosition) {
		super(gender, currentPosition);
	}
	
	@Override
	public String toString() {
		// String representation of CradleOfFilth object
		return "CoF";
	}
	
	public void increaseMaturity() {
		// adds 1 year to COF's maturity status
		this.cruelty++;
	}
	
}
