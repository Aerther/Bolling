package football.math;

public class Vector2d {
	public float x;
	public float y;
	
	public Vector2d() {
		this.x = 0;
		this.y = 0;
	}
	
	public Vector2d(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float dot(Vector2d vector) {
		float result = 0.0f;
		
		result = this.x * vector.getX() + this.y * vector.getY();
		
		return result;
	}
	
	public float length() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	public float getDistance(Vector2d vector) {
		float dx = this.x - vector.getX();
		float dy = this.y - vector.getY();
		
		return (float) Math.sqrt(dx * dx + dy * dy);
	}
	
	public Vector2d add(Vector2d vector) {
		var result = new Vector2d();
		
		result.setX(this.x + vector.getX());
		result.setY(this.y + vector.getY());
		
		return result;
	}
	
	public Vector2d subtract(Vector2d vector) {
		var result = new Vector2d();
		
		result.setX(this.x - vector.getX());
		result.setY(this.y - vector.getY());
		
		return result;
	}
	
	public Vector2d multiply(float scaleFactor) {
		var result = new Vector2d();
		
		result.setX(this.x * scaleFactor);
		result.setY(this.y * scaleFactor);
		
		return result;
	}
	
	public Vector2d normalize() {
		float len = this.length();
		
		if(len != 0.0f) {
			this.x = this.x / len;
			this.y = this.y / len;
		} else {
			this.x = 0.0f;
			this.y = 0.0f;
		}
		
		return this;
	}
	
	// Getters and Setters
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}

