//
//  ViewSduiFactory.swift
//  JsonSwiftUIConverter
//
//  Created by Anna Zharkova on 14.05.2024.
//

import Foundation
import SwiftUI

internal struct ViewSduiFactory {
    
    private let material: ViewMaterial
    
    init(material: ViewMaterial) {
        self.material = material
    }
    
    func text() {
        AnyView(Text("some").font(.title).fontWeight(.regular))
    }
    
    func scrollViewText()->String {
        if let subviews = material.subviews {
            
            let axisKey = material.properties?.axis ?? "vertical"
            let axis = Axis.Set.pick[axisKey] ?? .vertical
            let showsIndicators = material.properties?.showsIndicators ?? true
            
            let innerText = subviews.map{
                ViewSduiFactory(material: $0).printView()
            }.joined(separator: "\n")
            
            return """
                "ScrollView(\(axis), showsIndicators: \(showsIndicators)) {
                AxisBasedStack(axis: \(axis)) {
                    \(innerText)
                }
"""
        } else {
            return "Text(\"Please Add Subview for ScrollView\")"
        }
    }

    
    func listText()->String {
        if let subviews = material.subviews {
            let rows = subviews.map{
                ViewSduiFactory(material: $0).printView()
            }.joined(separator: "\n")
            return """
                VStack {
                \(rows)
            }
        """
        } else {
            return "Text(\"Please Add Subview for List\")"
        }
        
    }
    
 
    func vstackText() -> String {
        if let subviews = material.subviews {
            let spacing = material.properties?.spacing.toCGFloat() ?? 0
            let horizontalAlignmentKey = material.properties?.horizontalAlignment ?? "center"
            let horizontalAlignment = horizontalAlignmentKey
            let innerText = subviews.map{
                ViewSduiFactory(material: $0).printView()
            }.joined(separator: "\n")
            return """
                VStack(alignment: .\(horizontalAlignment), spacing: \(spacing)) {
                                   \(innerText)
        }
        """
        } else {
            return "Text(\"Please Add Subview for VStack\")"
        }
    }

    
    func hstackText() -> String{
        if let subviews = material.subviews {
            let spacing = material.properties?.spacing.toCGFloat() ?? 0
            let verticalAlignmentKey = material.properties?.verticalAlignment ?? "center"
            let verticalAlignment = VerticalAlignment.pick[verticalAlignmentKey] ?? .center
            let innerText = subviews.map {
                ViewSduiFactory(material: $0).printView()
            }.joined(separator: "\n")
            return """
            HStack(alignment: .\(verticalAlignmentKey), spacing: \(spacing)) {
                \(innerText)
            }
            """
        } else {
            return "Text(\"Please Add Subview for LazyHStack\")"
        }
    }
    
    func zstackText() -> String {
        
        if let subviews = material.subviews {
            let innerText = subviews.map{
                ViewSduiFactory(material: $0).printView()
            }.joined(separator: "\n")
            return """
          "ZStack {
                \(innerText)
            }
         """
        } else {
            return "Text(\"Please Add Subview for ZStack\")"
        }
    }
    
    func textText() -> String {
       let fontHashValue = material.properties?.font ?? "body"
       let font = Font.pick[fontHashValue]
       let fontWeightHashValue = material.properties?.fontWeight ?? "regular"
       let fontWeight = Font.Weight.pick[fontWeightHashValue]
       let text = material.values?.text ?? ""
       return """
        Text(\"\(text)\")
           .font(.\(fontHashValue))
           .fontWeight(.\(fontWeightHashValue))
        """
   }
    
    
    func imageText() -> String {
        if let systemIconName = material.values?.systemIconName {
            return """
        Image(systemName: \(systemIconName))
                .resizable()
                .scaledToFit()
        """
        } else if let localIconName = material.values?.localImageName {
            return """
         Image(\(localIconName))
                .resizable()
                .scaledToFit()
        """
            
        } else if let remoteUrl = material.values?.imageUrl {
            return """
        KFImage(URL(string: \"\(remoteUrl)\")!)
                .resizable()
                .scaledToFit()
        """
        } else {
            return """
        Text("Image value could not read")
        """
        }
    }
    
    // MARK: - Spacer
    @ViewBuilder func spacer() -> some View {
        let minLength = material.properties?.minLength.toCGFloat()
        Spacer(minLength: minLength)
    }
    
    
    
    func buildText()->String{
        switch material.type {
        case .ScrollView: return scrollViewText()
        case .List: return listText()
        case .VStack: return vstackText()
        case .Text: return textText()
        case .Image: return imageText()
        case .HStack: return hstackText()
        default: return ""
        }
    }
    
    
    func printView() -> String {
        let prop = material.properties
        
        let uiComponent = buildText() + ModifierFactory.PaddingModifier(padding: prop?.padding.toCGFloat()).output + ModifierFactory.ForegroundModifier(foregroundColor: prop?.foregroundColor.toColor()).output
            + ModifierFactory.BorderModifier(
                borderColor: prop?.borderColor.toColor(),
                borderWidth: prop?.borderWidth.toCGFloat()
            ).output
            + ModifierFactory.FrameModifier(
                width: prop?.width.toCGFloat(),
                height: prop?.height.toCGFloat()
            ).output
        return uiComponent
    }
    
}
