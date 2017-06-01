package br.com.devtools.apidevtools.resource.product;

import javax.ws.rs.Path;

import br.com.devtools.apidevtools.core.controller.Controller;

@Path("product")
public class ProductController extends Controller<Product> {

	@Override
	public Class<Product> getClasse() {
		return Product.class;
	}

}
