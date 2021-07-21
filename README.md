# Android Code Extension

## App capabilities
- Displays a list of listings to the user in a grid using RecyclerView
- Fetches these listings from a Reverb GraphQL API
- Includes tests for the IndexAdapter

## Instructions
1. We'll use your machine to drive. Ensure you are ready to go by having anything installed beforehand and that the project can run as-is. If you don't have access to a computer, **let us know** and we'll arrange an alternative setup.
1. Familiarize yourself with the the code.
1. That's it! We'll give you all other information during the coding session.

## Tips & Setup
- The project is compatabile Android Studio 4.2
- We'll be using GraphQL. We don't expect you to be an expert, but if you want to brush up, [these docs](https://graphql.org/learn/) summarize the spec and feature set nicely. We serve [GraphQL over HTTP](https://graphql.org/learn/serving-over-http/)
- GraphiQL is great to poke around our GraphQL schema.
  - `brew cask install graphiql` or available [here](https://github.com/skevy/graphiql-app/releases/download/v0.7.2/GraphiQL-0.7.2.dmg)
- We really like using [Charles Proxy](https://www.charlesproxy.com), the free version is fine. Using a proxy may be helpful, but it's not required.

## Libraries Used
- [Volley](https://developer.android.com/training/volley) for networking, though we don't expect to touch these APIs directly
- [Koin](https://insert-koin.io/) for dependency injection
- [Glide](https://github.com/bumptech/glide) for image loading
- [MockK](https://mockk.io/) for mocking in tests