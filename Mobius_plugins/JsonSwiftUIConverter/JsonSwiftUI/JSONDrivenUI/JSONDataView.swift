//
//  JSONDataView.swift
//  
//
//  Created by Enes Karaosman on 27.11.2020.
//

import SwiftUI

public struct JSONDataView: View, JSONDataViewProtocol {
    
    public var json: Data
    
    public var output: String = ""
    
    public init(json: Data) {
        self.json = json
    }
    
    public mutating func updateOutput(output: String) {
        self.output = output
    }
    
    @ViewBuilder public func toPresentable() -> some View {
        if let material = try? JSONDecoder().decode(
            ViewMaterial.self,
            from: json
        ) {
            let m = ViewFactory(material: material)
            let v = m.toPresentable()
            let output = m.printView()
            v
        } else {
            Text("Serialization Error")
        }
    }
    public var body: some View {
        toPresentable()
    }
}

//struct JSONDataView_Previews: PreviewProvider {
//    static var previews: some View {
//        JSONDataView()
//    }
//}
