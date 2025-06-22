namespace NLDiningApi.Models.DTO;

public class RestaurantReviewDto
{
    public decimal ReviewScoreFood { get; set; }
    public decimal ReviewScoreService { get; set; }
    public decimal ReviewScoreAmbiance { get; set; }
    public string LastReview { get; set; }
    
}