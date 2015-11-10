package entities.living;

public class CradleOfFilth extends LivingEntity {
	/* class for lowest hierarchy
	 * CrafleOfFilth has the following variables:
	 * 		String name,
	 * 		String gender,
	 * 		int age, --> at age 3, it turns into an Imp object
	 * 		boolean isAlive,
	 * 		int[] currentPosition,
	 * 		int cruelty
	 */
	
	public CradleOfFilth(String gender, int[] currentPosition) {
		super(gender, currentPosition);
	}
	
	@Override
	public String toString() {
		// String representation of CradleOfFilth object
		return "CoF";
	}
	
}
