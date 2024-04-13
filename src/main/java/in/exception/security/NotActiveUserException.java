package in.exception.security;

public class NotActiveUserException extends AuthorizationException{
	public NotActiveUserException (){
        super("Ваш аккаунт не активен");
    }
}
