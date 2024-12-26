package ravenexchange.backend.listing;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public List<Map<String, Object>> getAllListings(int offset, int limit, String category) {
        List<Map<String, Object>> listings = new ArrayList<>();
        List<Listing> listingEntities;

        if(offset < 0 || limit <= 0){
            throw new IllegalArgumentException("Offset must be greater than or equal to 0 and limit must be greater than 0");
        }

        if(category == null){
            listingEntities = listingRepository.findTop10ByOrderByListingIdDesc(); //Find by id in descending order
        }
        else {
            listingEntities = listingRepository.findTop10ByListingCategoryOrderByListingIdDesc(category); //Find by id in descending order with a specific category
        }

        for (int i = offset; i < offset + limit && i < listingEntities.size(); i++) {
            Listing listing = listingEntities.get(i);
            Map<String, Object> listingMap = listing.toMap();
            listings.add(listingMap);
        }

        return listings;
    }

    public Map<String, Object> getListing(Long listingId) {
        if(listingId == null){
            throw new IllegalArgumentException("Listing ID cannot be null");
        }

        if(listingId <= 0){
            throw new IllegalArgumentException("Listing ID must be greater than 0");
        }

        Listing listing = listingRepository.findByListingId(listingId);

        return listing.toMap();
    }
}
