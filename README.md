## Get Access Token from Authorization Server
```sh 
curl http://localhost:8080/oauth/token -d"grant_type=password&username=user1&password=password" -H"Content-type:application/x-www-form-urlencoded; charset=utf-8" -u myclient:secret
```

## Access Resource using Access Token
```sh
curl -H "Authorization: bearer <ACCESS_TOKEN>" http://localhost:9090/api/v1/status
```
