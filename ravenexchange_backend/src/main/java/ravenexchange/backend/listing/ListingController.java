package ravenexchange.backend.listing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param category Category of the listing
     * @return Returns a JSON response containing the status, message, size, and a list of dictionaries containing listing information
     */
    @GetMapping("/all")
    ResponseEntity<Map<String, Object>> getAllListings(
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "category", required = false) String category) {

        Map<String, Object> jsonResponse = new HashMap<>(); //JSON response map

        if(offset == null){ //Default 0
            offset = 0;
        }

        if(limit == null){ //Default 10
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

    /**
     * Gets a specific listing based on the listing id
     *
     * @param listingId Id of the listing
     * @return Returns a JSON response containing the status, message, and a dictionary containing listing information
     */
    @GetMapping("/get{name}")
    ResponseEntity<Map<String, Object>> getListing(@PathVariable("name") Long listingId) {

        Map<String, Object> jsonResponse = new HashMap<>(); //JSON response map

        try {
            Map<String, Object> listing = listingService.getListing(listingId);

            jsonResponse.put("message", "success");
            jsonResponse.put("status", HttpStatus.OK);
            jsonResponse.put("data", listing);

            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        }
        catch (Exception e){
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }

    /**
     * Creates a new listing
     *
     * @param listing Listing object containing listing information given via POST request in JSON format
     * @return Returns a JSON response containing the status and message
     */
    @PostMapping("/create")
    ResponseEntity<Map<String, Object>> createListing(@RequestBody Listing listing){
        Map<String, Object> jsonResponse = new HashMap<>(); //JSON response map

        try {
            listingService.createListing(listing.getSellerId(), listing.getListingName(), listing.getListingDescription(), listing.getListingPrice(), listing.getListingPicture(), listing.getListingCategory());

            jsonResponse.put("message", "success");
            jsonResponse.put("status", HttpStatus.CREATED);

            return ResponseEntity.status(HttpStatus.CREATED).body(jsonResponse);
        }
        catch (Exception e){
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

    }

    /**
     * Deletes a listing by id
     *
     * @param listingId Id of the listing to delete
     * @return Returns a JSON response containing the status and message
     */
    @PostMapping("/delete{name}")
    ResponseEntity<Map<String, Object>> deleteListing(@PathVariable("name") Long listingId){
        Map<String, Object> jsonResponse = new HashMap<>(); //JSON response map

        try {
            listingService.deleteListing(listingId);

            jsonResponse.put("message", "success");
            jsonResponse.put("status", HttpStatus.OK);

            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        }
        catch (Exception e){
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }

    /**
     * Updates a listing
     *
     * @param listing Listing object containing listing information given via POST request in JSON format
     * @return Returns a JSON response containing the status and message
     */
    @PostMapping("/update")
    ResponseEntity<Map<String, Object>> updateListing(@RequestBody Listing listing){
        Map<String, Object> jsonResponse = new HashMap<>(); //JSON response map

        try {
            listingService.updateListing(listing);

            jsonResponse.put("message", "success");
            jsonResponse.put("status", HttpStatus.OK);

            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        }
        catch (Exception e){
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }

}
