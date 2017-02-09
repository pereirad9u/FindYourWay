package boundary;

import control.KeyManagement;
import control.PasswordManagement;
import entity.Accreditation;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import org.mindrot.jbcrypt.BCrypt;

@Path("/authentification")
public class AuthentificationBoundary {

    @Inject
    private KeyManagement keyManagement;

    @Context
    private UriInfo uriInfo;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response authentifieUtilisateur(Accreditation accrediation) {
        try {
            String nomUtilisateur = accrediation.getUsername();
            String motDePasse = accrediation.getPassword();
            // On authentifie l'utilisateur en utilisant les crédentails fournis
            authentifie(nomUtilisateur, motDePasse);
            // On fournit un token
            String token = issueToken(nomUtilisateur);
            //System.out.println(token);
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authentifie(String nomUtilisateur, String motDePasse) throws Exception {
        System.out.println("hash : "+PasswordManagement.digestPassword(motDePasse));
        // On authentifie l'utilisateur en utilisant la BD, LDAP,...
        // On lance une exception si les crédentials sont invalides
        String motDePasseBD = "$2a$10$BHkbVLwpYUB4IIi47YUg.Ok0/oujUx4IzL6fH9zjLfrrwebZ2TE6C";
        if (nomUtilisateur.equals("root") && BCrypt.checkpw(motDePasse, motDePasseBD)) {
        } else {
            throw new NotAuthorizedException("Problème d'authentification");
        }
    }

    private String issueToken(String login) {
        Key key = keyManagement.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(5L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        System.out.println(">>>> token/key : " + jwtToken + " - " + key);
        return jwtToken;
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}

