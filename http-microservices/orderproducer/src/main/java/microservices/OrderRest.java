package microservices;

import microservices.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@Named
@Path("/")
public class OrderRest {

    boolean stockServiceOnline =true;

    @Inject
    private RestTemplate restTemplate;

    private static List<Order> Orders = new ArrayList<Order>();

    static {
        Order order1 = new Order();
        order1.setOrderId(1);
        order1.setProductCode("Prod1");
        order1.setQty(1);
        Orders.add(order1);

        Order order2 = new Order();
        order2.setOrderId(2);
        order2.setProductCode("Prod2");
        order2.setQty(2);
        Orders.add(order2);

        Order order3 = new Order();
        order3.setOrderId(3);
        order3.setProductCode("Prod3");
        order3.setQty(3);
        Orders.add(order3);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrders() {
        return Orders;
    }

    @GET
    @Path("order")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrder(@QueryParam("id") long id) {
        Order or = null;
        for (Order o : Orders) {
            if (o.getOrderId() == id)
                or = o;
        }
        return or;
    }


    /****** MAIN ONE ******/
    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("processOrder")
    public ProcessOrderResponse processOrder(final ProcessOrderRequest req){
        HttpEntity<String> myEnt = null;
        ResponseEntity<ProcessOrderResponse> orderResp;

        if (stockServiceOnline) {
            try {
                orderResp = restTemplate.exchange(
                        "http://localhost:8082/updateStock?orderId={orderId}&productCode={productCode}&qty={qty}", org.springframework.http.HttpMethod.GET, myEnt, ProcessOrderResponse.class, new Long(1), "Prod1", new Integer(2));
            } catch (Exception e) {
                System.out.print("Error returned from updateStock");
            }
            System.out.print("updateStock working");
        } else {
            return null;
        }
        return null;
    }


    @XmlRootElement
    public static class ProcessOrderRequest {
        @XmlElement
        String productCode;
        @XmlElement  Long orderId;
        @XmlElement
        int qty;
    }


    public static class ProcessOrderResponse {
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

}
