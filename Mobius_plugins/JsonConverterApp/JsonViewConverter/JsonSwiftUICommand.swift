//
//  JsonSwiftUICommand.swift
//  JsonViewConverter
//
//  Created by Anna Zharkova on 14.05.2024.
//

import Foundation
import XcodeKit
import AppKit

class JsonSwiftUICommand : NSObject, XCSourceEditorCommand  {
    func perform(with invocation: XCSourceEditorCommandInvocation, 
                 completionHandler: @escaping ((any Error)?) -> Void) {
        guard let json = NSPasteboard.general.string(forType: .string) else {
            return
        }
        let lines = invocation.buffer.lines
        if let data = json.data(using: .utf8) {
            let newLines = JsonSwiftUIGenerator.decodedViewString(json: data)
            lines.add(newLines)
        }
        completionHandler(nil)
    }
}

