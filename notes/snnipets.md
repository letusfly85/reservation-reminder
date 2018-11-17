```bash
curl -H 'Content-Type: application/json' -XPOST "localhost:8080/api/v1/reserves" -d '{
   "id": "1", 
   "reserved_user_id": "1", 
   "name": "test", 
   "reserved_from": "2018-10-11'T'12:00:00", 
   "reserved_to": "2018-10-11'T'12:00:00", 
   "description": "test" 
}'  | jq .
```
