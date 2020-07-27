package com.mycroft.sbdj.utils;

public class ConstantsUtil {
	
	//WORDS
	public static final String SLASH = "/";
	public static final String DOUBLE_SLASH = "//";
	public static final String POINT = ".";
	public static final String APP_JSON = "application/json";
	public static final String PORCENT = "%";
	
	//METHODS
	public static final String METHOD_REDIRECT = "redirect:";
	
	//URLS
	public static final String PATH_VIEW = "view";
	public static final String PATH_CREATE = "create";
	public static final String PATH_USER_VIEW = "user/view";
	public static final String PATH_USER_CREATE = "user/create";
	public static final String PATH_USER_EDIT = "user/edit/{id}";
	public static final String PATH_USER_DELETE = "user/delete/{id}";
	public static final String PATH_USER_PROFILE = "user/profile";
	public static final String PATH_USER_PROFILE_ID = "user/profile/{id}";
	public static final String PATH_PRODUCT_VIEW = "product/view";
	public static final String PATH_PRODUCT_CREATE = "product/create";
	public static final String PATH_PRODUCT_EDIT = "product/edit/{id}";
	public static final String PATH_PRODUCT_DELETE = "product/delete/{id}";
	public static final String PATH_BILL_VIEW = "bill/view";
	public static final String PATH_BILL_DETAIL_VIEW = "bill/view/{id}";
	public static final String PATH_BILL_CREATE = "bill/create";
	public static final String PATH_BILL_CREATE_ID = "bill/create/{id}";
	public static final String PATH_BILL_EDIT = "bill/edit/{id}";
	public static final String PATH_BILL_DELETE = "bill/delete/{id}";
	public static final String PATH_BILL_PRODUCT_TERM = "bill/products/{term}";
	
	//VARIABLE NAMES
	public static final String VARIABLE_NAME_SUCCESS = "success";
	public static final String VARIABLE_NAME_ERROR = "error";
	public static final String VARIABLE_NAME_WARNING = "warning";
	public static final String VARIABLE_NAME_INFO = "info";
	public static final String VARIABLE_NAME_FILE = "file";
	public static final String VARIABLE_NAME_PAGE = "page";
	public static final String VARIABLE_NAME_USER = "user";
	public static final String VARIABLE_NAME_PRODUCT = "product";
	public static final String VARIABLE_NAME_BILL = "bill";
	public static final String VARIABLE_NAME_BILL_DETAIL = "billDetail";
	public static final String VARIABLE_NAME_USERS = "users";
	public static final String VARIABLE_NAME_PRODUCTS = "products";
	public static final String VARIABLE_NAME_BILLS = "bills";
	public static final String VARIABLE_NAME_BILL_DETAILS = "billDetails";
	public static final String VARIABLE_NAME_ID = "id";
	public static final String VARIABLE_NAME_TERM = "term";
	public static final String VARIABLE_NAME_FILES_FOLDER = "uploads";
	public static final String VARIABLE_IS_PHOTO_EXIST = "isPhotoExist";
	public static final String VARIABLE_NAME_MESSAGE_FILE_DOESNT_EXIST = "messageFileDoesntExist";
	public static final String VARIABLE_NAME_MESSAGE_BILL_IS_EMPTY = "messageBillIsEmpty";

	//QUERIES
	//*User
	public static final String QUERY_USER_LISTS = "from User";
	
	//MESSAGES ALERT
	//*Success
	public static final String MESSAGE_SUCCESS_USER_CREATE = "El usuario {0} {1} ha sido creado correctamente";
	public static final String MESSAGE_SUCCESS_USER_EDIT = "El usuario {0} {1} ha sido modificado correctamente";
	public static final String MESSAGE_SUCCESS_USER_DELETE = "El usuario ha sido eliminado exitosamente";
	public static final String MESSAGE_SUCCESS_PRODUCT_CREATE = "El producto {0} ha sido creado correctamente";
	public static final String MESSAGE_SUCCESS_BILL_CREATE = "La factura de Folio: {0} ha sido creado correctamente";
	public static final String MESSAGE_SUCCESS_PRODUCT_EDIT = "El producto {0} ha sido modificado correctamente";
	public static final String MESSAGE_SUCCESS_PRODUCT_DELETE = "El producto ha sido eliminado exitosamente";
	//*Danger
	public static final String MESSAGE_DANGER_DELETE = "Error: No es posible borrar al usuario";
	public static final String MESSAGE_DANGER_ID_DOESNT_BE_ZERO = "Error: El ID no puede ser 0";
	public static final String MESSAGE_DANGER_UPLOAD_ERROR = "Error: No es posible subir la imagen";
	public static final String MESSAGE_DANGER_FILE_DOESNT_LOAD = "Error: No se puede cargar la imagen: {0}";
	public static final String MESSAGE_DANGER_FILE_DOESNT_EXIST = "Error: El archivo no existe";
	public static final String MESSAGE_DANGER_FILE_DOESNT_DELETE = "Error: No es posible eliminar la imagen: {0}";
	public static final String MESSAGE_DANGER_USER_DOESNT_EXIST = "Error: El usuario no existe";
	public static final String MESSAGE_DANGER_PRODUCT_DOESNT_EXIST = "Error: El producto no existe";
	public static final String MESSAGE_DANGER_PRODUCT_NO_STOCK = "Error: El stock del producto '{0}' tiene menos stock '{1}' que la cantidad ingresada '{2}'";
	public static final String MESSAGE_DANGER_BILL_DOESNT_EXIST = "Error: La factura no existe";
	public static final String MESSAGE_DANGER_BILL_DETAILS_EMPTY = "Error: No existen productos en la factura";
	//*Warning
	public static final String MESSAGE_WARNING_FILE_TO_DELETE_EXISTING = "Advertencia: El archivo se ha encontrado en el directorio, se procederá a eliminar.";	
	public static final String MESSAGE_WARNING_BILL_IS_EMPTY = "Advertencia: No se encontraron facturas asociadas al usuario";
	public static final String MESSAGE_WARNING_FILE_DOESNT_EXIST = "Advertencia: No se encontró una imagen asociada al usuario";
	//*Info
	public static final String MESSAGE_INFO_UPLOAD = "Se ha subido el archivo correctamente. Nombre: {0}";
	public static final String MESSAGE_INFO_CREATE_DIRECTORY_UPLOADS = "INFO: Creando directorio Uploads...";
	public static final String MESSAGE_INFO_DELETE_DIRECTORY_UPLOADS = "INFO: Borrando directorio Uploads y sus archivos...";
	
	//TITLE
	public static final String TITLE_PROFILE_USER = "Perfil de {0} {1}";
}
