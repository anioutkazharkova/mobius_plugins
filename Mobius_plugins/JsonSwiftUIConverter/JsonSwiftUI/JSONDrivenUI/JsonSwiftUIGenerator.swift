//
//  JsonSwiftUIGenerator.swift
//  JsonSwiftUIConverter
//
//  Created by Anna Zharkova on 14.05.2024.
//

import Foundation

class JsonSwiftUIGenerator {
    static let shared = JsonSwiftUIGenerator()
    
    
    public static func printView(json: Data) {
        if let material = try? JSONDecoder().decode(
            ViewMaterial.self,
            from: json
        ) {
            let m = ViewSduiFactory(material: material)
            let output = """
             struct SwiftUIView : View {
                var body: some View {
                  \(m.printView())
                }
"""
           print(output)
        }
    }
    
}
