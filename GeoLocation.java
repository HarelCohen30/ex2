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
		// TODO Auto-generated method stub
		return 0;
	}

}
