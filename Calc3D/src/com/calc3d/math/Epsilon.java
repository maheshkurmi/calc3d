package com.calc3d.math;
/**
 * Class containing an approximation of machine epsilon.
 */
public class Epsilon {
	/** The double precision floating point machine epsilon approximation */
	public static final double E = Epsilon.compute();
	
	/**
	 * Hidden default constructor.
	 */
	private Epsilon() {}
	
	/**
	 * Computes an approximation of machine epsilon.
	 * @return double
	 */
	public static final double compute() {
		double e = 0.5;
		while (1.0 + e > 1.0) {
			e *= 0.5;
		}
		return e;
	}
}
