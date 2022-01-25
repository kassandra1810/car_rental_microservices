echo "LILIA'S CONFIGURATION START"

couchbase-cli cluster-init -c localhost --cluster-username Administrator --cluster-password Administrator --services data,index,query --cluster-ramsize 512 --cluster-index-ramsize 256

couchbase-cli bucket-create --cluster localhost:8091 --username Administrator --password Administrator --bucket orders --bucket-type couchbase --bucket-ramsize 512 --max-ttl 500000000 --durability-min-level persistToMajority --enable-flush 0

couchbase-cli user-manage --cluster localhost --username Administrator --password Administrator --set --rbac-username user --rbac-password password --roles bucket_full_access[orders] --auth-domain local

echo "LILIA'S CONFIGURATION END"