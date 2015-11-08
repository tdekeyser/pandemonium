package entities;

public class CradleOfFilth extends LivingEntity {
	
	int maturity; // variable for maturity measure
	
	public CradleOfFilth(String gender, int[] currentPosition) {
		super(gender, currentPosition);
	}
	
	public void increaseMaturity() {
		// adds 1 year to COF's maturity status
		this.maturity++;
	}
	
}
