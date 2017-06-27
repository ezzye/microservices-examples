package microservices;

import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ellioe03 on 27/06/2017.
 */
@Named
@Path("/")
public class StockProxyRest {

    @Inject
    private RestTemplate restTemplate;


    @GET
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("updateStock")
    public OrderResponse updateStock(@QueryParam("orderId") Long orderId, @QueryParam("productCode") String productCode, @QueryParam("qty") Integer qty){
        OrderRequest r = new OrderRequest();
        r.orderId=orderId;
        r.productCode=productCode;
        r.qty=qty;
        OrderResponse resp = null;
        try {
            resp = restTemplate.postForObject("http://localhost:8080/updateStockNew", r, OrderResponse.class);
        } catch(Exception e) {
            System.out.print("sadasdasdas");
        }
        return resp;
    }


//    @POST @Consumes("application/json")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("updateStock")
//    public OrderResponse updateStock(final OrderRequest req){
//        return null;
//    }



    public static class OrderResponse {
        private Long orderId;
        private String productCode;
        private Boolean success;

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }
    }
    @XmlRootElement
    public static class OrderRequest {
        @XmlElement
        String productCode;
        @XmlElement  Long orderId;
        @XmlElement
        int qty;

    }

}
