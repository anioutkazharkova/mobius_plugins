//
//  ViewFactory.swift
//  
//
//  Created by Enes Karaosman on 27.11.2020.
//

import SwiftUI
import Kingfisher

internal struct ViewFactory: PresentableProtocol {
    
    private let material: ViewMaterial
    
    init(material: ViewMaterial) {
        self.material = material
    }
    
    // MARK: - ScrollView
    @ViewBuilder func scrollView() -> some View {
        if let subviews = material.subviews {
            
            let axisKey = material.properties?.axis ?? "vertical"
            let axis = Axis.Set.pick[axisKey] ?? .vertical
            let showsIndicators = material.properties?.showsIndicators ?? true
            
            ScrollView(axis, showsIndicators: showsIndicators) {
                AxisBasedStack(axis: axis) {
                    ForEach(subviews) { (subview) in
                        ViewFactory(material: subview).toPresentable()
                    }
                }
            }
        } else {
            Text("Please Add Subview for ScrollView")
        }
    }
    
    func scrollViewText()->String {
        if let subviews = material.subviews {
            
            let axisKey = material.properties?.axis ?? "vertical"
            let axis = Axis.Set.pick[axisKey] ?? .vertical
            let showsIndicators = material.properties?.showsIndicators ?? true
            
            let innerText = subviews.map{
                ViewFactory(material: $0).printView()
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
    
    // MARK: - List
    @ViewBuilder func list() -> some View {
        if let subviews = material.subviews {
            List(subviews) {
                ViewFactory(material: $0).toPresentable()
            }
        } else {
            Text("Please Add Subview for List")
        }
    }
    
    func listText()->String {
        if let subviews = material.subviews {
            let rows = subviews.map{
                ViewFactory(material: $0).printView()
            }.joined(separator: "\n")
            return """
" VStack {
                \(rows)
            }
"""
        } else {
            return "Text(\"Please Add Subview for List\")"
        }
        
    }
    
    // MARK: - VStack
    @ViewBuilder func vstack() -> some View {
        if let subviews = material.subviews {
            let spacing = material.properties?.spacing.toCGFloat() ?? 0
            let horizontalAlignmentKey = material.properties?.horizontalAlignment ?? "center"
            let horizontalAlignment = HorizontalAlignment.pick[horizontalAlignmentKey] ?? .center
            VStack(alignment: horizontalAlignment, spacing: spacing) {
                ForEach(subviews) {
                    ViewFactory(material: $0).toPresentable()
                }
            }
        } else {
            Text("Please Add Subview for VStack")
        }
    }
    
    func vstackText() -> String {
        if let subviews = material.subviews {
            let spacing = material.properties?.spacing.toCGFloat() ?? 0
            let horizontalAlignmentKey = material.properties?.horizontalAlignment ?? "center"
            let horizontalAlignment = horizontalAlignmentKey
            let innerText = subviews.map{
                ViewFactory(material: $0).printView()
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
    
    // MARK: - LazyVStack
    @ViewBuilder func lazyVstack() -> some View {
        if let subviews = material.subviews {
            let spacing = material.properties?.spacing.toCGFloat() ?? 0
            let horizontalAlignmentKey = material.properties?.horizontalAlignment ?? "center"
            let horizontalAlignment = HorizontalAlignment.pick[horizontalAlignmentKey] ?? .center
            LazyVStack(alignment: horizontalAlignment, spacing: spacing) {
                ForEach(subviews) {
                    ViewFactory(material: $0).toPresentable()
                }
            }
        } else {
            Text("Please Add Subview for LazyVStack")
        }
    }
    
    // MARK: - HStack
    @ViewBuilder func hstack() -> some View {
        if let subviews = material.subviews {
            let spacing = material.properties?.spacing.toCGFloat() ?? 0
            let verticalAlignmentKey = material.properties?.verticalAlignment ?? "center"
            let verticalAlignment = VerticalAlignment.pick[verticalAlignmentKey] ?? .center
            HStack(alignment: verticalAlignment, spacing: spacing) {
                ForEach(subviews) {
                    ViewFactory(material: $0).toPresentable()
                }
            }
        } else {
            Text("Please Add Subview for LazyHStack")
        }
    }
    
    func hstackText() -> String{
        if let subviews = material.subviews {
            let spacing = material.properties?.spacing.toCGFloat() ?? 0
            let verticalAlignmentKey = material.properties?.verticalAlignment ?? "center"
            let verticalAlignment = VerticalAlignment.pick[verticalAlignmentKey] ?? .center
            let innerText = subviews.map {
                ViewFactory(material: $0).printView()
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
    
    // MARK: - HStack
    @ViewBuilder func lazyHstack() -> some View {
        if let subviews = material.subviews {
            let spacing = material.properties?.spacing.toCGFloat() ?? 0
            let verticalAlignmentKey = material.properties?.verticalAlignment ?? "center"
            let verticalAlignment = VerticalAlignment.pick[verticalAlignmentKey] ?? .center
            LazyHStack(alignment: verticalAlignment, spacing: spacing) {
                ForEach(subviews) {
                    ViewFactory(material: $0).toPresentable()
                }
            }
        } else {
            Text("Please Add Subview for HStack")
        }
    }
    
    // MARK: - ZStack
    @ViewBuilder func zstack() -> some View {
        if let subviews = material.subviews {
            ZStack {
                ForEach(subviews) {
                    ViewFactory(material: $0).toPresentable()
                }
            }
        } else {
            Text("Please Add Subview for ZStack")
        }
    }
    
    func zstackText() -> String {
        
        if let subviews = material.subviews {
            let innerText = subviews.map{
                ViewFactory(material: $0).printView()
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
    
    // MARK: - Text
    @ViewBuilder func text() -> some View {
        let fontHashValue = material.properties?.font ?? "body"
        let font = Font.pick[fontHashValue]
        let fontWeightHashValue = material.properties?.fontWeight ?? "regular"
        let fontWeight = Font.Weight.pick[fontWeightHashValue]
        Text(material.values?.text ?? "")
            .font(font)
            .fontWeight(fontWeight)
    }
    
    func textText() -> String {
       let fontHashValue = material.properties?.font ?? "body"
       let font = Font.pick[fontHashValue]
       let fontWeightHashValue = material.properties?.fontWeight ?? "regular"
       let fontWeight = Font.Weight.pick[fontWeightHashValue]
       return """
        Text(\(material.values?.text ?? ""))
           .font(.\(fontHashValue))
           .fontWeight(.\(fontWeightHashValue))
        """
   }
    
    // MARK: - Image
    @ViewBuilder func image() -> some View {
        if let systemIconName = material.values?.systemIconName {
            Image(systemName: systemIconName)
                .resizable()
                .scaledToFit()
        } else if let localIconName = material.values?.localImageName {
            Image(localIconName)
                .resizable()
                .scaledToFit()
            
        } else if let remoteUrl = material.values?.imageUrl {
            KFImage(URL(string: remoteUrl))
                .resizable()
                .scaledToFit()
        } else {
            Text("Image value could not read")
        }
    }
    
    func imageText() -> String {
        if let systemIconName = material.values?.systemIconName {
            return """
        "Image(systemName: \(systemIconName))
                .resizable()
                .scaledToFit()
        """
        } else if let localIconName = material.values?.localImageName {
            return """
         "Image(\(localIconName))
                .resizable()
                .scaledToFit()
        """
            
        } else if let remoteUrl = material.values?.imageUrl {
            return """
        "KFImage(URL(string: \(remoteUrl)))
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
    
    @ViewBuilder func buildDefault() -> some View {
        switch material.type {
        case .ScrollView: scrollView()
        case .List: list()
        case .LazyVStack: lazyVstack()
        case .LazyHStack: lazyHstack()
        case .VStack: vstack()
        case .HStack: hstack()
        case .ZStack: zstack()
        case .Text: text()
        case .Image: image()
        case .Spacer: spacer()
        case .Rectangle: Rectangle()
        case .Divider: Divider()
        case .Circle: Circle()
        default: EmptyView()
        }
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
    
    @ViewBuilder func toPresentable() -> some View {
        
        let prop = material.properties
        
        let uiComponent = buildDefault().embedInAnyView()
        uiComponent
            .modifier(ModifierFactory.PaddingModifier(padding: prop?.padding.toCGFloat()))
            .modifier(ModifierFactory.ForegroundModifier(foregroundColor: prop?.foregroundColor.toColor()))
            .modifier(ModifierFactory.BorderModifier(
                borderColor: prop?.borderColor.toColor(),
                borderWidth: prop?.borderWidth.toCGFloat()
            ))
            .modifier(ModifierFactory.FrameModifier(
                width: prop?.width.toCGFloat(),
                height: prop?.height.toCGFloat()
            ))
        
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
        return "AnyView(\(uiComponent))"
    }
    
}
