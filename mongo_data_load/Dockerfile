FROM mongo:latest

# Create data folders
COPY Product.json /Product.json
COPY User.json /User.json
COPY Auction.json /Auction.json
COPY script.sh /script.sh

RUN chmod +x script.sh
CMD [ "./script.sh" ]

ENTRYPOINT [ ]