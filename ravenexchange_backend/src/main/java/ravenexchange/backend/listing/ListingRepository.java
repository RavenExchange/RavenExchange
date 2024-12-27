package ravenexchange.backend.listing;

import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource
public interface ListingRepository extends JpaRepository<Listing, Long> {
    @Query("SELECT l FROM Listing l ORDER BY l.listingId DESC")
    List<Listing> findTop10ByOrderByListingIdDesc(); // Fetch the top 10 entries based on listing_id in descending order

    @Query("SELECT l FROM Listing l WHERE l.listingCategory = :category ORDER BY l.listingId DESC")
    List<Listing> findTop10ByListingCategoryOrderByListingIdDesc(@Param("category") String category); // Fetch the top 10 entries based on listing_id in descending order with a specific category

    // Find a listing by listing_id
    Listing findByListingId(Long listingId);

    List<Listing> findByListingNameContaining(String listingName);
}
