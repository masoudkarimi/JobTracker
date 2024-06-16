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
        
        init() {
            Task {
                do {
                    let db = Database(databaseDriver: DatabaseDriver())
                    let emittedValues = try await db.getAllApplicationStatusesList()
                    
                    if emittedValues.isEmpty {
                        try await db.insertNewApplicationStatus(name: "Wishlist")
                        try await db.insertNewApplicationStatus(name: "Applied")
                        try await db.insertNewApplicationStatus(name: "Interview")
                    }
                    
                    NSLog("Adding items done!")
                } catch {
                    NSLog(error.localizedDescription)
                }
                
            
            }
        }
        
        func startObserving() async {
            let db = Database(databaseDriver: DatabaseDriver())
            let emittedValues = await db.getAllApplicationStatuses()
            for await item in emittedValues {
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
