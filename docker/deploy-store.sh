#! / bin / bash
if [ $(id -u) -eq 0 ]; then

  echo "**SCRIPT BACKEND BUSCA BASE API**\n"

  docker stop store-api
	if [ $? -eq 0 ]; then
		echo "Parando o container store-api"
	else
		echo "Erro ao parar o container store-api"
	fi

	docker stop mongodb-store
	if [ $? -eq 0 ]; then
		echo "Parando o container mongodb-store"
	else
		echo "Erro ao parar o container mongodb-store"
	fi

	docker rm legalboxplus_busca_base
	if [ $? -eq 0 ]; then
		echo "Removendo o container store-api"
	else
		echo "Erro remover o container store-api"
	fi

	docker rmi legalboxplus_busca_base
	if [ $? -eq 0 ]; then
		echo "Removendo a imagem do store-api"
	else
		echo "Erro ao remover a imagem store-api"
	fi

	docker network create store-network
	if [ $? -eq 0 ]; then
		echo "criando network store-network"
	else
		echo "Não foi criado a network store-network"
	fi

	docker run -d --name mongodb-store --network store-network -p 27017:27017 -e MONGODB_USERNAME=admin -e MONGODB_PASSWORD=123456 -e MONGODB_DATABASE=store bitnami/mongodb:latest
	if [ $? -eq 0 ]; then
		echo "Construindo container store-api"
	else
		echo "Erro ao construir o container store-api"
	fi

	docker build --no-cache -t store-api -f Dockerfile-store-api .
	if [ $? -eq 0 ]; then
		echo "Construindo container store-api"
	else
		echo "Erro ao construir o container store-api"
	fi
store-api
	docker run -d -p 8090:8090 --name legalboxplus_busca_base --network store-network store-api
	if [ $? -eq 0 ]; then
    echo "-------- Run store-api ---------"
  else
    echo "Erro Rodar store-api"
    return 0;
  fi

else
	echo "Only root may add a user to the system"
	exit 2
fi