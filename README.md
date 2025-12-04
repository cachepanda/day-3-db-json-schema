# Day 3: Log DB with a Schema and JSON encoding of data
A database that appends json records to a log file and uses an in-memory hash index for fast lookups.

Currently it hardcodes the schema object as a class Person.

## How It Works
### set(Person)

Append json record  to the end of the file database.ndjson
Store the id and starting byte offset in the in-memory hash index

### get(id)

Look up the offset in the hash index
Seek directly to that position in the file
Read the line and return the parsed json into Person class. 
