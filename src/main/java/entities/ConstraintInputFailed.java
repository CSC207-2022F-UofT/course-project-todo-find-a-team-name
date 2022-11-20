package entities;

public class ConstraintInputFailed extends RuntimeException{
    public ConstraintInputFailed(String errorMessage){
        super(errorMessage);
    }
}
