package ravenexchange.backend.listing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/listing")
public class ListingController {
    private final ListingService listingService;

    //ListingController constructor
    ListingController(ListingRepository listingRepository) {
        this.listingService = new ListingService(listingRepository);
    }

    /**
     * Gets specified number of listings in descending order based on id,
     * skipping a specific amount depending on the offset
     *
     * @param offset Number of listings to skip
     * @param limit Number of listings to get
     * @return Returns a JSON response containing the status, message, size, and a list of dictionaries containing listing information
     */
    @GetMapping("/all")
    ResponseEntity<Map<String, Object>> getAllListings(
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "category", required = false) String category) {

        Map<String, Object> jsonResponse = new HashMap<>(); //JSON response map

        if(offset == null){
            offset = 0;
        }

        if(limit == null){
            limit = 10;
        }

        try {
            List<Map<String, Object>> listings = listingService.getAllListings(offset, limit, category);

            jsonResponse.put("message", "success");
            jsonResponse.put("status", HttpStatus.OK);
            jsonResponse.put("size", listings.size());
            jsonResponse.put("data", listings);

            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        }
        catch (Exception e){
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }
}
