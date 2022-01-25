set -m

/entrypoint.sh couchbase-server &

echo "WAIT FOR COUCHBASE"
sleep 45


echo "LILIA'S CONFIGURATION START"

couchbase-cli cluster-init -c 127.0.0.1:8091 --cluster-name myCluster --cluster-username Administrator --cluster-password Administrator --services data,index,query,fts --cluster-ramsize 512 --cluster-index-ramsize 512 --cluster-fts-ramsize 512 --index-storage-setting default	
	
couchbase-cli bucket-create --cluster 127.0.0.1:8091 --username Administrator --password Administrator --bucket orders --bucket-type couchbase --bucket-ramsize 512

couchbase-cli user-manage --cluster 127.0.0.1 --username Administrator --password Administrator --set --rbac-username user --rbac-password password --roles query_select[orders],query_delete[orders],query_insert[orders],query_update[orders],bucket_admin[orders] --auth-domain local

couchbase-cli server-list -c 127.0.0.1:8091 --username Administrator --password Administrator


echo "WAIT FOR QUERY SERVICE IS READY"
sleep 45

cd opt/couchbase/bin

./cbq -u Administrator -p Administrator -e "http://127.0.0.1:8091" --script="create primary index on orders;"

echo "LILIA'S CONFIGURATION END"

fg 1




