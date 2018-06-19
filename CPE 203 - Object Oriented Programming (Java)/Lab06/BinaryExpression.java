public abstract class BinaryExpression implements Expression
{
    private Expression lft;
    private Expression rht;
    private String operator;

    public BinaryExpression(Expression lft, Expression rht, String operator)
    {
    	this.lft = lft;
      	this.rht = rht;
      	this.operator = operator;  
    }

    public String toString()
    {
        return ("(" + lft + "" + operator + "" + rht + ")");
    }

    public double evaluate(final Bindings bindings)
    {
    	double L = lft.evaluate(bindings); 
    	double R = rht.evaluate(bindings);

        return applyOperator(L,R);
    }
  
    protected abstract double applyOperator(double L, double R);
}