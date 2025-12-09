# DIAL AI Chat Application - Java Implementation

A Java implementation of a command-line chat application that communicates with the DIAL AI API, supporting both synchronous and streaming completions.

## 🎯 Project Overview

This project is a Java 21 implementation of a chat application that interfaces with EPAM's DIAL AI service. The application demonstrates how to work with AI APIs, handle REST requests/responses, and implement real-time streaming communication with Large Language Models (LLMs).

**Source Repository**: This implementation is based on the original Python task from: [https://github.com/khshanovskyi/ai-dial-chat-completions](https://github.com/khshanovskyi/ai-dial-chat-completions)

## 🎓 Key Features

- **Interactive Command-Line Interface**: User-friendly chat experience with conversation history
- **Dual Communication Modes**: Support for both synchronous and streaming API calls
- **Conversation Management**: Maintains conversation history for context-aware responses
- **Custom System Prompts**: Allows users to define custom system prompts or use defaults
- **Real-time Streaming**: Live streaming of AI responses character by character
- **Error Handling**: Robust error handling for network and API issues

## 🛠 Technology Stack

- **Java 21** - Modern Java with latest features
- **Maven** - Dependency management and build tool
- **Jackson** - JSON parsing and serialization
- **Java HTTP Client** - Built-in HTTP client for API communication
- **CompletableFuture** - Asynchronous programming support

## 📁 Project Structure

```
src/main/java/com/epam/autocode/
├── ChatApplication.java           # Main application entry point
├── clients/
│   ├── BaseClient.java           # Abstract base class for API clients
│   └── CustomDialClient.java     # DIAL API client implementation
├── constants/
│   └── Constants.java            # Configuration constants
└── models/
    ├── Conversation.java         # Conversation management
    ├── Message.java             # Message data model
    └── Role.java                # Message role enumeration
```

## 🔧 Setup and Installation

### Prerequisites

- **Java 21** or higher
- **Maven 3.6+**
- **DIAL API Key** from EPAM
- **EPAM VPN** connection

### Installation Steps

1. **Clone or download the project**
   ```bash
   git clone <repository-url>
   cd sandbox
   ```

2. **Configure API Key**
   
   Update the API key in `src/main/java/com/epam/autocode/constants/Constants.java`:
   ```java
   public static final String API_KEY = "your-dial-api-key-here";
   ```
   
   Or set it as an environment variable:
   ```bash
   set DIAL_API_KEY=your-api-key
   ```

3. **Build the project**
   ```bash
   mvn clean compile
   ```

4. **Run the application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.epam.autocode.ChatApplication"
   ```
   
   Or from your IDE, run the `ChatApplication.main()` method.

## 🚀 Usage

### Starting the Application

When you run the application, you'll see:

```
🤖 Provide System prompt or press ⏎ 'enter' to continue.
🧑‍💻 >
```

### Setting System Prompt

You can either:
- **Enter a custom system prompt** to define the AI's behavior
- **Press Enter** to use the default prompt: "You are an assistant who answers concisely and informatively."

### Chat Interaction

```
❓ Type your question or 'exit' to quit.
> Hello, how are you?
🤖 AI:
Hello! I'm just a program, so I don't have feelings, but I'm here and ready to help. How can I assist you today?

🧑‍💻 > What's the weather like?
🤖 AI:
I can't provide real-time weather updates, but you can check current conditions using...

🧑‍💻 > exit
👋 Exiting the chat. Goodbye!
```

## 🏗 Architecture

### Core Components

#### 1. **ChatApplication**
- Main entry point and user interface
- Manages conversation flow and user input
- Handles both streaming and non-streaming modes

#### 2. **BaseClient**
- Abstract base class defining the client interface
- Manages API key validation
- Defines contract for completion methods

#### 3. **CustomDialClient**
- Concrete implementation of DIAL API client
- Handles HTTP requests to DIAL endpoint
- Implements both synchronous and streaming communication
- Parses JSON responses and streaming data

#### 4. **Models**
- **Message**: Represents individual chat messages with role and content
- **Role**: Enum for message roles (SYSTEM, USER, AI)
- **Conversation**: Manages message history and conversation state

### API Integration

The application integrates with EPAM's DIAL AI service:

- **Endpoint**: `https://ai-proxy.lab.epam.com/openai/deployments/{model}/chat/completions`
- **Model**: GPT-4o
- **Authentication**: API key via `api-key` header
- **Content-Type**: `application/json`

### Streaming Implementation

The streaming functionality:

1. Sets `"stream": true` in the request payload
2. Processes Server-Sent Events (SSE) format responses
3. Parses each `data:` chunk containing JSON
4. Extracts content from `choices[0].delta.content`
5. Displays content in real-time as it arrives
6. Handles the `[DONE]` termination signal

## 📊 Example Use Cases

### 1. Calculator Assistant
```
System Prompt: You are a calculator. Your role is to perform mathematical computations and output the result as a number. Do NOT include any words, explanations, or units in your responses. Only provide the numeric result of the calculation.

🧑‍💻 > 3*8
🤖 AI: 24

🧑‍💻 > /2
🤖 AI: 12
```

### 2. Technical Troubleshooting
```
System Prompt: You are a Python expert and troubleshooting specialist...

🧑‍💻 > I'm getting an error while running my Python script.
🤖 AI: What's the error message you're seeing?

🧑‍💻 > 'ModuleNotFoundError: No module named requests'.
🤖 AI: This means the 'requests' library isn't installed. You can install it by running `pip install requests`...
```

## 🔧 Configuration

### Constants Configuration

```java
public class Constants {
    public static final String DEFAULT_SYSTEM_PROMPT = "You are an assistant who answers concisely and informatively.";
    public static final String DIAL_ENDPOINT = "https://ai-proxy.lab.epam.com";
    public static final String API_KEY = "your-api-key-here";
}
```

### Maven Dependencies

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.16.1</version>
</dependency>
```

## 🧪 Testing Scenarios

The application supports various conversation patterns:

- **Single Q&A**: Simple question-answer interactions
- **Multi-turn Conversations**: Context-aware conversations with history
- **Role-based Interactions**: Different AI personalities via system prompts
- **Mathematical Calculations**: Precise computational responses
- **Technical Support**: Problem-solving conversations

## 🔒 Security Notes

- API keys are sensitive information - avoid committing them to version control
- Use environment variables for production deployments
- Ensure VPN connection when accessing EPAM's DIAL service
- Validate and sanitize user inputs

## 📈 Performance Features

- **Asynchronous Processing**: Non-blocking streaming responses
- **Connection Pooling**: Reusable HTTP connections
- **Timeout Handling**: 30-second connection timeout
- **Memory Efficient**: Streaming processing without buffering entire responses

## 🛠 Development

### Building from Source

```bash
# Compile
mvn compile

# Run tests
mvn test

# Package
mvn package

# Clean build
mvn clean compile
```

### IDE Setup

The project is compatible with:
- **IntelliJ IDEA**
- **Eclipse**
- **Visual Studio Code**

Import as a Maven project and ensure Java 21 is configured.

## 📋 Requirements Met

✅ **Streaming in Console**: Real-time character-by-character output  
✅ **Conversation History Support**: Full conversation context maintained  
✅ **Error Handling**: Robust exception handling and user feedback  
✅ **Modular Design**: Clean separation of concerns  
✅ **Type Safety**: Strong typing with Java records and enums  


## 📄 License

This project is part of EPAM's educational materials and follows company guidelines for internal use.

---

**Note**: This is a Java implementation of the originally Python-based DIAL AI Chat Completion task, demonstrating equivalent functionality using modern Java features and best practices.
