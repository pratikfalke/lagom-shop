package be.yannickdeturck.lagomshop.item.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;
import org.pcollections.PSequence;

public interface ItemService extends Service {

    /**
     * Example: curl http://localhost:9000/api/items/1
     */
    ServiceCall<NotUsed, Item> getItem(String id);

    /**
     * Example: curl http://localhost:9000/api/items
     */
    ServiceCall<NotUsed, PSequence<Item>> getAllItems();

    /**
     * Example: curl -H "Content-Type: application/json" -X POST -d
     * '{"name": "Chair", "price": 10.50}' http://localhost:9000/api/items
     */
    ServiceCall<AddItemRequest, AddItemResponse> createItem();

    @Override
    default Descriptor descriptor() {
        return Service.named("itemservice").with(
                Service.restCall(Method.GET,  "/api/items/:id", this::getItem),
                Service.restCall(Method.GET,  "/api/items", this::getAllItems),
                Service.restCall(Method.POST, "/api/items", this::createItem)
        ).withAutoAcl(true);
    }
}
