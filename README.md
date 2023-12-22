# About The Project

This project helps to manage literature database, using XML and JAXB. Users can load, validate, and create databases, add/remove authors/publications, list authors/publications, print XML to the console, and save XML to a file — streamlining literature data management.


## Built with

- Java
- JAXB 
- Gradle 


## Installation

1. Clone the repo from GitHub
  ```sh
   git clone https://github.com/anastasiia-syvtsova/Management_of_lit_db.git
   ```
2. Open cloned repo in integrated development environment (ex. IntelliJ IDEA, Eclipse, Visual Studio)
3. Start the program via main method. 



## Usage

Upon initiating the program, an automatic launch of the main menu occurs, featuring the following options:
 
```java
1. Load and Validate Literature Database
2. Create New Literature Database
0. Exit System

```

The user is required to input the corresponding option number in the terminal and press enter. 

If the user opts to load and validate the literature database, they must provide the file path for loading.

Alternatively, selecting the option to create a new literature database opens a sub-menu with the following choices:

```java
1. Add Author
2. Remove Author
3. Add Publication
4. Remove Publication
5. List Authors
6. List Publications
7. Print XML on Console
8. Save XML to File
0. Back to main menu / close without saving

```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.
