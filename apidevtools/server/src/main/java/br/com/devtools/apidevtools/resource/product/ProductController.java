package br.com.devtools.apidevtools.resource.product;

import javax.ws.rs.Path;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.permission.PermissionClass;
import io.swagger.annotations.Api;

@Api("Produto")
@Path("product")
@PermissionClass(description="Produto")
public class ProductController extends Controller<Product> {

	@Override
	public Class<Product> getClasse() {
		return Product.class;
	}

}
