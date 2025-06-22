using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

[Table("restaurants")]
public class Restaurant
{
    [Key]
    [Column("Id")]
    public int Id { get; set; }

    [Column("Name")]
    public string Naam { get; set; }

    [Column("Category")]
    public string Categorie { get; set; }

    [Column("Tags")]
    public string Tags { get; set; }

    [Column("Address")]
    public string Adres { get; set; }

    [Column("ReviewScoreOverall", TypeName = "decimal(4,2)")]
    public decimal? ReviewScoreOverall { get; set; }

    [Column("ReviewScoreFood", TypeName = "decimal(4,2)")]
    public decimal? ReviewScoreFood { get; set; }

    [Column("ReviewScoreService", TypeName = "decimal(4,2)")]
    public decimal? ReviewScoreService { get; set; }

    [Column("ReviewScoreAmbiance", TypeName = "decimal(4,2)")]
    public decimal? ReviewScoreAmbiance { get; set; }

    [Column("ReviewCount")]
    public int ReviewCount { get; set; }

    [Column("LastReview")]
    public string LastReview { get; set; }
}