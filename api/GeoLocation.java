package api;

public class GeoLocation implements geo_location{

	double x;
	double y;
	double z;

	public GeoLocation (double x,double y,double z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}

	public GeoLocation(String pos) {

		int a = pos.indexOf(',');
		String A = pos.substring(0, a);
		x = Double.parseDouble(A);

		int b = pos.indexOf(',', a+1);
		String B = pos.substring(a+1, b);
		y = Double.parseDouble(B);

		String C = pos.substring(b+1);
		z = Double.parseDouble(C);

	}

	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	public double z() {
		return z;
	}

	public double distance(geo_location g) {
		double a = x*x;
		double b = y*y;
		double c = z*z;
		double dist = Math.sqrt(a+b+c);
		return dist;
	}

}