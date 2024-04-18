package exceptions.security;

public class NotActiveUserException extends AuthorizationException{
	public NotActiveUserException (){
        super("Ваш аккаунт не активен");
    }
}
