public class Textbook
{
	private String name, code, publisher;
	private byte grade;

    public Textbook(String name, byte grade, String publisher, String code)
    {
        this.name = name;
        this.grade = grade;
        this.code = code;
        this.publisher = publisher;
    }

    public String getName()
    {
        return name;
    }
    
    public byte getGrade()
    {
    	return grade;
    }
    
    public String getPublisher()
    {
    	return publisher;
    }
    
    public String getCode()
    {
    	return code;
    }
}
