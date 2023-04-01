FROM mongo

COPY Auction.json /Auction.json 
COPY Product.json /Product.json
COPY User.json /User.json
COPY init.sh /init.sh

RUN chmod +x init.sh

CMD ./init.sh
