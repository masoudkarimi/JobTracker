import SwiftUI
import Shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel
    
    var body: some View {
        Button("Click me!") {
            Task {
                await viewModel.startObserving()
            }
        }
    }
}



extension ContentView {
    
    @MainActor
    class ViewModel: ObservableObject {
        @Published var values: [String] = []
        
        private let repository = KoinHelper().applicationStatusRepository
        
        init() {
//            Task {
//                do {
//                    try await repository.insert(name: "Wishlist")
//                    try await repository.insert(name: "Applied")
//                    try await repository.insert(name: "Interview")
//                    
//                    
//                    NSLog("Adding items done!")
//                } catch {
//                    NSLog(error.localizedDescription)
//                }
//            }
        }
        
        func startObserving() async {
            for await item in repository.flow {
                NSLog("Item received \(item)")
            }
        }
        
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(viewModel: ContentView.ViewModel())
    }
}
