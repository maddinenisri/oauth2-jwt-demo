## Get Access Token from Authorization Server
```sh 
curl http://localhost:8080/oauth/token -d"grant_type=password&username=user1&password=password" -H"Content-type:application/x-www-form-urlencoded; charset=utf-8" -u myclient:secret
```

## Access Resource using Access Token
```sh
curl -H "Authorization: bearer <ACCESS_TOKEN>" http://localhost:9090/api/v1/status
```

## Generate Key Store
```sh
keytool -genkeypair -alias mykeys -keyalg RSA -keypass mypass -keystore mykeys.jks -storepass mypass
```

## Copy Key store into Auth server classpath

## Extract Public Key
```sh
keytool -list -rfc --keystore mykeys.jks  | openssl x509 -inform pem -pubkey
```

## Copy Public key text into Resource server  ( public.cert)

```sh
curl -H "Authorization: bearer <access token>" http://localhost:9090/api/v1/status
```
WIth invalid token {"error":"access_denied","error_description":"Invalid token does not contain resource id (resource1)"}

### Reference: [Oauth2 Study](http://www.swisspush.org/security/2016/10/17/oauth2-in-depth-introduction-for-enterprises)
