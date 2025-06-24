using FirebaseAdmin.Auth;
using Microsoft.AspNetCore.Http;
using System.Threading.Tasks;

namespace NldiningAPI.Middleware
{
    public class FirebaseAuthMiddleware
    {
        private readonly RequestDelegate _next;

        public FirebaseAuthMiddleware(RequestDelegate next)
        {
            _next = next;
        }

        public async Task InvokeAsync(HttpContext context)
        {
            if (!context.Request.Headers.TryGetValue("Authorization", out var authHeader) || !authHeader.ToString().StartsWith("Bearer "))
            {
                context.Response.StatusCode = 401;
                await context.Response.WriteAsync("Token ontbreekt of is ongeldig.");
                return;
            }

            var token = authHeader.ToString().Replace("Bearer ", "").Trim();

            try
            {
                var decodedToken = await FirebaseAuth.DefaultInstance.VerifyIdTokenAsync(token);
                context.Items["FirebaseUser"] = decodedToken;
                await _next(context);
            }
            catch
            {
                context.Response.StatusCode = 401;
                await context.Response.WriteAsync("Token verificatie mislukt.");
            }
        }
    }
}