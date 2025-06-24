using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using NLDiningApi.Models.DTO;

namespace NLDiningApi.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class RestaurantsController : ControllerBase
    {
        private readonly AppDbContext _context;

        public RestaurantsController(AppDbContext context)
        {
            _context = context;
        }
        

        [HttpPost("{id}/review")]
        public async Task<IActionResult> PostReview(int id, [FromBody] RestaurantReviewDto review)
        {
            var restaurant = await _context.Restaurants.FirstOrDefaultAsync(r => r.Id == id);

            if (restaurant == null)
                return NotFound("Restaurant niet gevonden.");

            var previousReviews = restaurant.ReviewCount;
            var newTotal = previousReviews + 1;

            var food = Math.Round(((restaurant.ReviewScoreFood ?? 0m) * previousReviews + (decimal)review.ReviewScoreFood) / newTotal, 2);
            var service = Math.Round(((restaurant.ReviewScoreService ?? 0m) * previousReviews + (decimal)review.ReviewScoreService) / newTotal, 2);
            var ambiance = Math.Round(((restaurant.ReviewScoreAmbiance ?? 0m) * previousReviews + (decimal)review.ReviewScoreAmbiance) / newTotal, 2);

            var calculatedOverall = Math.Round(((decimal)review.ReviewScoreFood + (decimal)review.ReviewScoreService + (decimal)review.ReviewScoreAmbiance) / 3, 2);
            var overall = Math.Round(((restaurant.ReviewScoreOverall ?? 0m) * previousReviews + calculatedOverall) / newTotal, 2);

            restaurant.ReviewScoreFood = food;
            restaurant.ReviewScoreService = service;
            restaurant.ReviewScoreAmbiance = ambiance;
            restaurant.ReviewScoreOverall = overall;
            restaurant.ReviewCount = newTotal;
            restaurant.LastReview = review.LastReview;

            await _context.SaveChangesAsync();

            return Ok(new
            {
                message = "Review succesvol verwerkt.",
                updated = restaurant
            });
        }
        
        [HttpGet("filter")]
        public async Task<IActionResult> FilterRestaurants(
            [FromQuery] string? plaats,
            [FromQuery] string? categorie,
            [FromQuery] string? tags,
            [FromQuery] float? minScoreOverall,
            [FromQuery] float? minScoreFood,
            [FromQuery] float? minScoreService,
            [FromQuery] float? minScoreAmbiance)
        {
            var query = _context.Restaurants.AsQueryable();
            
            if (!string.IsNullOrEmpty(categorie))
            {
                query = query.Where(r => r.Categorie != null && r.Categorie.Contains(categorie));
            }

            if (!string.IsNullOrEmpty(tags))
            {
                query = query.Where(r => r.Tags != null && r.Tags.Contains(tags));
            }

            if (minScoreOverall.HasValue)
            {
                query = query.Where(r => r.ReviewScoreOverall >= (decimal)minScoreOverall.Value);
            }

            if (minScoreFood.HasValue)
            {
                query = query.Where(r => r.ReviewScoreFood >= (decimal)minScoreFood.Value);
            }

            if (minScoreService.HasValue)
            {
                query = query.Where(r => r.ReviewScoreService >= (decimal)minScoreService.Value);
            }

            if (minScoreAmbiance.HasValue)
            {
                query = query.Where(r => r.ReviewScoreAmbiance >= (decimal)minScoreAmbiance.Value);
            }
            
            if (!string.IsNullOrEmpty(plaats))
            {
                var plaatsLower = plaats.ToLower();

                query = query.Where(r => r.Adres != null && (
                    EF.Functions.Like(r.Adres.ToLower(), plaatsLower) ||
                    EF.Functions.Like(r.Adres.ToLower(), plaatsLower + " %") ||
                    EF.Functions.Like(r.Adres.ToLower(), "% " + plaatsLower + " %") ||
                    EF.Functions.Like(r.Adres.ToLower(), "% " + plaatsLower)
                ));
            }

            var results = await query.ToListAsync();
            return Ok(results);
        }

    }
}
